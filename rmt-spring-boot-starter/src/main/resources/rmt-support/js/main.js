// web console
const action = function (type, data) {
    return JSON.stringify({
        type,
        ...data,
    });
}
const XtermAction = {
    xterm: undefined,
    ws: undefined,
    needInitAgain: function () {
        return null === this.xterm || undefined === this.xterm;
    },
    setWs: function (ws) {
        this.ws = ws;
        return this;
    },
    heartbeat: function () {
        setInterval(() => {
            if (this.ws.readyState !== WebSocket.OPEN) {
                return;
            }
            this.sendProxy(action('TERMINAL_HEARTBEAT'))
        }, 5000)
    },
    sendProxy: function (message) {
        if (this.ws.readyState === WebSocket.CONNECTING) {
            this.xterm.showOverlay("WebSocket Connecting.")
            return;
        }
        if (this.ws.readyState === WebSocket.CLOSING || this.ws.readyState === WebSocket.CLOSED) {
            this.xterm.showOverlay("WebSocket Connection Closed.")
            return;
        }
        this.ws.send(message)
    },

    //todo
    setIntervalAction: function (func, timeInterval) {

    },
    initXterm: function (element) {
        const {cols, rows} = this.getTerminalSize();
        this.xterm = new Terminal({
            cols: cols,
            rows: rows,
            screenReaderMode: true,
            rendererType: 'canvas',
            convertEol: true,
            cursorBlink: true,
            cursorStyle: "block"
        });
        this.xterm.open(element, true);
        this.xterm.toggleFullscreen(true);
    },
    resize: function () {
        let {cols, rows} = this.getTerminalSize()
        this.sendProxy(action('TERMINAL_RESIZE', {cols, rows}))
        this.xterm.showOverlay(`${cols} x ${rows}`)
    },
    getCharSize: function () {
        const tempDiv = $('<div />').attr({'role': 'listitem'});
        const tempSpan = $('<div />').html('qwertyuiopasdfghjklzxcvbnm');
        tempDiv.append(tempSpan);
        $("html body").append(tempDiv);
        const size = {
            width: tempSpan.outerWidth() / 26,
            height: tempSpan.outerHeight(),
            left: tempDiv.outerWidth() - tempSpan.outerWidth(),
            top: tempDiv.outerHeight() - tempSpan.outerHeight(),
        };
        tempDiv.remove();
        return size;
    },
    getWindowSize: function () {
        let e = window;
        let a = 'inner';
        if (!('innerWidth' in window)) {
            a = 'client';
            e = document.documentElement || document.body;
        }
        const terminalDiv = document.getElementById("terminal");
        const terminalDivRect = terminalDiv.getBoundingClientRect();
        return {
            width: terminalDivRect.width,
            height: e[a + 'Height'] - terminalDivRect.top
        };
    },
    getTerminalSize: function () {
        const charSize = this.getCharSize();
        const windowSize = this.getWindowSize();
        console.log('charsize');
        console.log(charSize);
        console.log('windowSize');
        console.log(windowSize);
        return {
            cols: Math.floor((windowSize.width - charSize.left) / 10),
            rows: Math.floor((windowSize.height - charSize.top) / 25)
        };
    }
}
let retryInterval = undefined;
const happyWork = () => {
    let ws;
    let sec = location.protocol.indexOf("https") > -1
    if (sec) {
        ws = new WebSocket(`wss://${location.host}/terminal`)
    } else {
        ws = new WebSocket(`ws://${location.host}/terminal`)
    }

    // xterm
    const xa = Object.create(XtermAction)
    xa.setWs(ws)
    ws.onopen = (event) => {
        // 1. init xterm.js
        if (xa.needInitAgain()) {
            xa.initXterm(document.getElementById('#terminal'));
            xa.xterm.on('data', command => {
                xa.sendProxy(
                    action('TERMINAL_COMMAND', {
                        command,
                    })
                );
            });
        }
        // 2. check need retries
        if (null != retryInterval || undefined !== retryInterval) {
            clearInterval(retryInterval);
            retryInterval = undefined;
        }
        setTimeout(() => {
            xa.xterm.showOverlay("WebSocket Connect Success.")
        }, 2000)

        // 3. send resize request
        xa.resize();
        // 4.register  resize listener
        window.addEventListener("resize", function (event) {
            xa.resize()
        })
        // 5. heartbeat
        xa.heartbeat();
    }

    ws.onmessage = (event) => {
        let data = JSON.parse(event.data);
        switch (data.type) {
            case 'TERMINAL_PRINT':
                xa.xterm.write(data.text);
                break
            case 'TERMINAL_HEARTBEAT':
                console.log(data.text) // ok
                break
            default:
                break
        }
    };

    ws.onerror = (event) => {
        console.log(event);
    };

    ws.onclose = (event) => {
        xa.xterm.showOverlay("WebSocket Connection Closed.")
        if (null != retryInterval || undefined !== retryInterval) {
            console.log("has register interval")
            return;
        }
        retryInterval = setInterval(() => {
            happyWork()
        }, 5000);
    };
}
window.onload = function () {
    happyWork()
}