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

    }

}