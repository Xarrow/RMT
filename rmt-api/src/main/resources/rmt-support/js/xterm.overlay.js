Terminal.prototype.showOverlay = function (e, t) {
    if (!this.overlayNode_) {
        if (!this.element) return;
        this.overlayNode_ = document.createElement("div"), this.overlayNode_.style.cssText = "border-radius: 15px;font-size: xx-large;opacity: 0.75;padding: 0.2em 0.5em 0.2em 0.5em;position: absolute;-webkit-user-select: none;-webkit-transition: opacity 180ms ease-in;-moz-user-select: none;-moz-transition: opacity 180ms ease-in;", this.overlayNode_.addEventListener("mousedown", function (e) {
            e.preventDefault(), e.stopPropagation()
        }, !0)
    }
    this.overlayNode_.style.color = "#101010", this.overlayNode_.style.backgroundColor = "#f0f0f0", this.overlayNode_.textContent = e, this.overlayNode_.style.opacity = "0.75", this.overlayNode_.parentNode || this.element.appendChild(this.overlayNode_);
    var o = this.element.getBoundingClientRect(), i = this.overlayNode_.getBoundingClientRect();
    this.overlayNode_.style.top = (o.height - i.height) / 2 + "px", this.overlayNode_.style.left = (o.width - i.width) / 2 + "px";
    var l = this;
    this.overlayTimeout_ && clearTimeout(this.overlayTimeout_), null !== t && (this.overlayTimeout_ = setTimeout(function () {
        l.overlayNode_.style.opacity = "0", l.overlayTimeout_ = setTimeout(function () {
            l.overlayNode_.parentNode && l.overlayNode_.parentNode.removeChild(l.overlayNode_), l.overlayTimeout_ = null, l.overlayNode_.style.opacity = "0.75"
        }, 200)
    }, t || 1500))
};