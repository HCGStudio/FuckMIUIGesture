package com.hcgstudio.miui.fuck.gesture

import android.content.Intent
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
                findClass("androidx.preference.Preference").hook {
                    injectMember {
                        method {
                            name = "setIntent"
                            beforeHook {
                                val old = args(0).any() as Intent
                                if (old.action == "com.miui.home.action.navigation_bar_type_settings") {
                                    val intent = Intent()
                                    intent.setClassName(
                                        "com.hcgstudio.miui.fuck.gesture",
                                        "com.hcgstudio.miui.fuck.gesture.SettingsActivity"
                                    )
                                    args(0).set(intent)
                                }
                            }
                        }
                    }
                }

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

            loadApp(name = "com.miui.voiceassist") {
                findClass("android.provider.Settings\$Global").hook {
                    injectMember {
                        method {
                            name = "putInt"
                        }
                        beforeHook {
                            if (args(1).string() == "force_fsg_nav_bar") {
                                args(2).set(1)
                            }
                        }
                    }
                }
            }
        }
    }

}