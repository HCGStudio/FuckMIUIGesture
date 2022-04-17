package com.hcgstudio.miui.fuck.gesture

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.xposed.proxy.YukiHookXposedInitProxy

@InjectYukiHookWithXposed(modulePackageName = "com.hcgstudio.miui.fuck.gesture")
class MainHook : YukiHookXposedInitProxy {
    override fun onHook() {
        YukiHookAPI.encase {

            loadApp(name = "com.android.systemui") {
                findClass("com.android.systemui.recents.MiuiFullScreenGestureProxy").hook {
                    injectMember {
                        method {
                            name = "updateDefaultHome"
                        }
                        replaceTo({})
                    }
                }

                val strongMode = prefs.get(DataConst.STRONG_MODE_DATA)

                //Strong mode hook
                if (strongMode) {
                    findClass("android.provider.MiuiSettings\$Global").hook {
                        injectMember {
                            method {
                                name = "getBoolean"
                                returnType = BooleanType
                            }
                            afterHook {
                                if (args(1).string() == "force_fsg_nav_bar") {
                                    resultTrue()
                                }
                            }
                        }
                    }
                }
            }

            loadApp(name = "com.miui.home") {
                findClass("com.miui.home.launcher.common.Utilities").hook {
                    injectMember {
                        method {
                            name = "isUseMiuiHomeAsDefaultHome"
                            returnType = BooleanType
                        }
                        replaceToTrue()
                    }
                }
            }
        }
    }

}