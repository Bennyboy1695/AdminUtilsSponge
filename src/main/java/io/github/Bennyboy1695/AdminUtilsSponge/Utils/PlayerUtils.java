package io.github.Bennyboy1695.AdminUtilsSponge.Utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;

import java.util.Optional;
import java.util.UUID;

/**
 * io.github.Bennyboy1695.AdminUtilsSponge.Utils was created by Bennyboy1695 on 14/04/2017.
 * This mod is licensed to be that if its on github is considered to be open source,
 * but this doesnt mean my code can be used anywhere i haven't used it myself.
 */
public class PlayerUtils {

    public static UUID getUUIDFromName(String name) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(name);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer.get().getUniqueId();
        } else {
            Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
            Optional<User> optUser = userStorage.get().get(name);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return user.getUniqueId();
            }
        }
        return null;
    }

    public static String getNameFromUUID(UUID uuid) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(uuid);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer.get().getName();
        } else{
            Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
            Optional<User> optUser = userStorage.get().get(uuid);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return user.getName();
            }
        }
        return null;
    }

    public static User getPlayerFromUUID(UUID uuid) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(uuid);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer.get();
        } else{
            Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
            Optional<User> optUser = userStorage.get().get(uuid);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return user;
            }
        }
        return null;
    }

    public static User getPlayerFromName(String name) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(name);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer.get();
        } else{
            Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
            Optional<User> optUser = userStorage.get().get(name);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return user;
            }
        }
        return null;
    }

}
