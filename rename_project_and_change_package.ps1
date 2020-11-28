(gc settings.gradle) -replace "rootProject.name = .*", "rootProject.name = '$( $args[ 0 ] )'" | Out-File -encoding ASCII settings.gradle

(gc src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java) -replace "@Plugin.*", "@Plugin( name = `"$( $args[ 0 ] )`", version = `"1.0.0`" )" | Out-File -encoding ASCII src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java

(gc src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java) -replace "package com.maknmakstudios.minecraft..*", "package com.maknmakstudios.minecraft.$( $args[ 1 ] );" | Out-File -encoding ASCII src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java

(gc src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java) -replace "public class.*", "public class $( $args[ 0 ] ) extends JavaPlugin {" | Out-File -encoding ASCII src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java

Rename-Item -Path "src/com/maknmakstudios/minecraft/spigotplugintemplate/Main.java" -NewName "$( $args[ 0 ] ).java"

Rename-Item -Path "src/com/maknmakstudios/minecraft/spigotplugintemplate" -NewName "$( $args[ 1 ] )"
