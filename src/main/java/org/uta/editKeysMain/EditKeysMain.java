package org.uta.editKeysMain;

import org.bukkit.plugin.java.JavaPlugin;
import org.uta.EditKeys.Commands.Commands;
import org.uta.EditKeys.Commands.TabComp;
import org.uta.EditKeys.Item.EditItemKeys;
import org.uta.EditKeys.Player.EditPlayerKeys;

import java.util.Objects;

public final class EditKeysMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin 起動時の処理
        EditItemKeys.initialize(this);
        EditPlayerKeys.initialize(this);

        Objects.requireNonNull(getCommand("editkeys")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("editkeys")).setTabCompleter(new TabComp());
    }

    @Override
    public void onDisable() {
        // Plugin 停止時の処理（必要に応じて追加）
    }
}
