# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.

# Сохраняем UseCase и его зависимости
-keep class com.example.comon.server.domain.useCases.ConnectTaggerUseCase { *; }
-keep class com.example.comon.server.domain.useCases.** { *; }

# Сохраняем все классы в пакете comon (если нужно)
-keep class com.example.comon.** { *; }

# Правила для Hilt/Dagger
-keep class * { @javax.inject.Inject <init>(...); }
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModel
-keep class * implements dagger.hilt.internal.aggregatedroot.codegen.** { *; }

# Сохранение ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Сохранение UseCases
-keep class com.example.comon.**.domain.useCases.** { *; }
-keep class com.example.comon.**.domain.use_cases.** { *; }

-keep class com.example.comon.server.domain.useCases.ConnectTaggerUseCase { *; }
-keep class com.example.comon.server.domain.useCases.** { *; }

# Сохранение Repository
-keep class com.example.comon.**.data.** { *; }
-keep class com.example.comon.**.domain.repository.** { *; }
-keep class com.example.mainscreen.data.repository.** { *; }

# Сохранение ViewModel и связанных классов
-keep class com.example.featuregame.presentation.** { *; }
-keep class com.example.mainscreen.presentation.** { *; }
-keep class com.example.setings.** { *; }

# Сохранение WebSocket и других важных классов
-keep class com.example.comon.game.data.WebSocketServer { *; }
-keep class com.example.comon.server.domain.useCases.** { *; }

# Сохранение Navigation
-keep class com.example.navigation.** { *; }

# Сохраняем конкретный UseCase класс
-keep class com.example.comon.server.domain.useCases.TaggersInfoUseCase { *; }

# Подавление предупреждений (добавьте ВСЕ эти правила)
-dontwarn com.example.comon.Teams.domain.domain.repository.TeamsRepositoryInterface
-dontwarn com.example.comon.Teams.domain.domain.useCases.ChangeTeamNameUseCase
-dontwarn com.example.comon.Teams.domain.domain.useCases.TeamsUseCase
-dontwarn com.example.comon.game.data.GameRepository
-dontwarn com.example.comon.game.data.WebSocketServer
-dontwarn com.example.comon.game.domain.GameRepositoryInterface
-dontwarn com.example.comon.game.domain.use_cases.ChangeFriendlyFireModeUseCase
-dontwarn com.example.comon.game.domain.use_cases.ChangeGameTimeUseCase
-dontwarn com.example.comon.game.domain.use_cases.ChangeTimeBeforeStartUseCase
-dontwarn com.example.comon.game.domain.use_cases.GameLogsUpdateUseCase
-dontwarn com.example.comon.game.domain.use_cases.GameStartUseCase
-dontwarn com.example.comon.game.domain.use_cases.GameStopUseCase
-dontwarn com.example.comon.game.domain.use_cases.GameUseCase
-dontwarn com.example.comon.server.data.ServerRepository
-dontwarn com.example.comon.server.domain.repository.ServerRepositoryInterface
-dontwarn com.example.comon.server.domain.useCases.ChangeTaggerInfoUseCase
-dontwarn com.example.comon.server.domain.useCases.ChangeTeamUseCase
-dontwarn com.example.comon.server.domain.useCases.ConnectTaggerUseCase
-dontwarn com.example.comon.server.domain.useCases.TaggerConfigUpdateUseCase
-dontwarn com.example.comon.server.domain.useCases.TaggerInfoGameResMapperUseCase
-dontwarn com.example.comon.server.domain.useCases.TaggersInfoUseCAse
-dontwarn com.example.featuregame.presentation.GameViewModel
-dontwarn com.example.featuregame.presentation.GameViewModel_HiltModules$KeyModule
-dontwarn com.example.featuregame.presentation.GameViewModel_HiltModules_BindsModule_Binds_LazyMapKey
-dontwarn com.example.featuregame.presentation.GameViewModel_HiltModules_KeyModule_Provide_LazyMapKey
-dontwarn com.example.mainscreen.data.repository.TeamsRepository
-dontwarn com.example.mainscreen.presentation.ServerViewModel
-dontwarn com.example.mainscreen.presentation.ServerViewModel_HiltModules$KeyModule
-dontwarn com.example.mainscreen.presentation.ServerViewModel_HiltModules_BindsModule_Binds_LazyMapKey
-dontwarn com.example.mainscreen.presentation.ServerViewModel_HiltModules_KeyModule_Provide_LazyMapKey
-dontwarn com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
-dontwarn com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel_HiltModules$KeyModule
-dontwarn com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel_HiltModules_BindsModule_Binds_LazyMapKey
-dontwarn com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel_HiltModules_KeyModule_Provide_LazyMapKey
-dontwarn com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel
-dontwarn com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel_HiltModules$KeyModule
-dontwarn com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel_HiltModules_BindsModule_Binds_LazyMapKey
-dontwarn com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel_HiltModules_KeyModule_Provide_LazyMapKey
-dontwarn com.example.navigation.navGraph.ComonNavigationKt
-dontwarn com.example.setings.SettingsViewModel
-dontwarn com.example.setings.SettingsViewModel_HiltModules$KeyModule
-dontwarn com.example.setings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey
-dontwarn com.example.setings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey
-dontwarn com.example.comon.server.domain.useCases.TaggersInfoUseCase

-dontwarn com.example.featuregame.presentation.GameViewModel_HiltModules$BindsModule
-dontwarn com.example.mainscreen.data.di.NetworkModule
-dontwarn com.example.mainscreen.presentation.ServerViewModel_HiltModules$BindsModule
-dontwarn com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel_HiltModules$BindsModule
-dontwarn com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel_HiltModules$BindsModule
-dontwarn com.example.setings.SettingsViewModel_HiltModules$BindsModule
-dontwarn hilt_aggregated_deps._com_example_featuregame_presentation_GameViewModel_HiltModules_BindsModule
-dontwarn hilt_aggregated_deps._com_example_featuregame_presentation_GameViewModel_HiltModules_KeyModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_data_di_NetworkModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_ServerViewModel_HiltModules_BindsModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_ServerViewModel_HiltModules_KeyModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_actionTopBar_ActionTopBarViewModel_HiltModules_BindsModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_actionTopBar_ActionTopBarViewModel_HiltModules_KeyModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_taggerTeams_ConnectedTaggerViewModel_HiltModules_BindsModule
-dontwarn hilt_aggregated_deps._com_example_mainscreen_presentation_taggerTeams_ConnectedTaggerViewModel_HiltModules_KeyModule
-dontwarn hilt_aggregated_deps._com_example_setings_SettingsViewModel_HiltModules_BindsModule
-dontwarn hilt_aggregated_deps._com_example_setings_SettingsViewModel_HiltModules_KeyModule
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE