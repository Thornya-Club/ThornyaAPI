package test.thornyaplugin.thornyaplugin.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import thornyaplugin.thornyaplugin.ThornyaPlugin;
import thornyaplugin.thornyaplugin.taxas.Economy;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.*;

import static thornyaplugin.thornyaplugin.utils.Utils.formatarMoney;

/**
 * Utils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/24/2021</pre>
 */
public class UtilsTest extends TestCase {

    private ServerMock server;

    public UtilsTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {

        server = MockBukkit.mock();

        super.setUp();

    }

    public void tearDown() throws Exception {

        MockBukkit.unmock();

        super.tearDown();

    }

    public void testFormatarMoneyFloat() throws Exception {
        final String formatarMoney = formatarMoney(10F);
        Assert.assertEquals(formatarMoney, "§cT10.00");
    }

    public void testFormatarMoneyBigDecimal() throws Exception {
        final String formatarMoney = formatarMoney(new BigDecimal(10));
        Assert.assertEquals(formatarMoney, "§cT10.00");
    }

    public void testSenderPagar() throws Exception {

        PlayerMock player = server.addPlayer();
        String[] strings = {"total"};
        //senderTaxas(player, strings);

    }

    public static Test suite() {
        return new TestSuite(UtilsTest.class);
    }
}
