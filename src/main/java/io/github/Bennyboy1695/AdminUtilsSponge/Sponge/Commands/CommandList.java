package io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands;

import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PermissionStrings;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.caching.UserData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Optional;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands was created by Bennyboy1695 on 26/04/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class CommandList implements CommandExecutor {

    final LuckPermsApi api = LuckPerms.getApi();



    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<LuckPermsApi> provider = Sponge.getServiceManager().provide(LuckPermsApi.class);
        if (provider.isPresent()) {
            final LuckPermsApi api = provider.get();
        }

        ArrayList<Player> onlinePlayersList = new ArrayList();
        ArrayList<Text> paginationText = new ArrayList();
        Player sender = (Player) src;

        for (Player x : onlinePlayersList) {
            String currentWorld = x.getWorld().getName();
            boolean staff = x.hasPermission(PermissionStrings.liststaff);
            String group = api.getUser(x.getUniqueId()).getPrimaryGroup();
            Integer prefixweight = api.getGroup(group).getWeight().getAsInt();
            User user = api.getUserSafe(x.getUniqueId()).orElse(null);
            if (user == null) {
                continue;
            }
            UserData userData = user.getUserDataCache().orElse(null);
            Contexts contexts = api.getContextForUser(user).orElse(null);
            MetaData metaData = userData.getMetaData(contexts);
            String prefix = metaData.getPrefix();

        }

        return CommandResult.success();
    }
}
