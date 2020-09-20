package org.helixcs.rmt.expand.listener;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/19/2020.
 * @Desc:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(TerminalListenerRegistrar.class)
public @interface EnableTerminalListener {

}
