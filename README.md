# Fuck MIUI Gesture

[![Build status](https://build.appcenter.ms/v0.1/apps/522a086b-c255-4ee7-9978-49495cacca27/branches/main/badge)](https://appcenter.ms)

## English Brief

Enable this module to allow setting system gesture as nagation when using third party launcher.

Strong Mode: Force using gesture all the time, regardless the setting.

Supported Version: MIUI 13, LSPosed. MIUI 12.5 is unspported since manually set `force_fsg_nav_bar` also not working. See [#1](https://github.com/HCGStudio/FuckMIUIGesture/issues/1).

**NOTE: You STILL need tools for back, such as [Fluid Navigation Gestures](https://play.google.com/store/apps/details?id=com.fb.fluid).**

# 说明

众所周知，金凡限制在使用第三方启动器时使用手势。当然，我们可以使用魔法解除限制。

``` bash
settings put global force_fsg_nav_bar 1
```

但是，这个魔法在每次重启系统或者重启系统UI的时候失效，而且大概每天都需要重新执行一次，造成很多不便。使用这个模块就可以破除限制。

启用这个模块就可以破除这个限制，可以使用第三方启动器时使用手势，而且禁用了系统UI启动时的检查。

仅支持MIUI13+LSPosed，其他hook框架不保证能使用。不支持MIUI12.5，因为就算你手动设置`force_fsg_nav_bar`也只是会隐藏导航按键，而不会启用手势。

暴力模式：永久使手势生效，无论设置如何。

**注意：你仍然需要工具提供返回功能，比如[流体手势导航](https://play.google.com/store/apps/details?id=com.fb.fluid)。**
