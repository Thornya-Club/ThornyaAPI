package thornyaplugin.thornyaplugin.taxas.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.event.ShopPurchaseEvent;
import org.maxgamer.quickshop.shop.ShopType;
import org.maxgamer.quickshop.util.Util;
import thornyaplugin.thornyaplugin.ThornyaPlugin;
import thornyaplugin.thornyaplugin.taxas.Tax;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class QuickShop implements Listener {
    private ThornyaPlugin pl;

    public QuickShop(ThornyaPlugin plugin){
        this.pl = plugin;
    }
    @EventHandler
    public void TaxPreBuy(ShopPurchaseEvent e) {
        if (e.getShop().getShopType() == ShopType.BUYING) {
            e.getShop().isUnlimited();
            if (e.getShop().ownerName().equals(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(Objects.requireNonNull(pl.translate().getString("no-tax").replace("&", "§")));
            } else {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat formato = new DecimalFormat("####.##", symbols);
                double taxValue = e.getTotal() + (pl.tax.taxValue(e.getShop().getPrice()) * e.getAmount());
                double taxValueUnique = pl.tax.taxValue(e.getShop().getPrice());

                double taxtotal = taxValueUnique * e.getAmount();
                if(!e.getShop().isUnlimited()) {
                    //Se for um player
                    double balance = pl.ess.getUser(e.getShop().getOwner()).getMoney().doubleValue();
                    if (Tax.hasMoney(balance, taxValue)) {
                        pl.ess.getUser(e.getShop().getOwner()).sendMessage("§aVocê foi taxado em §2T" + formato.format(taxtotal) + " §anessa compra!");

                        e.setTotal(taxValue);
                        pl.ess.getUser(e.getPlayer().getPlayer()).takeMoney(BigDecimal.valueOf(Double.parseDouble(formato.format(taxtotal))));//tira a taxa do dono do shop
                        pl.sqlitetaxas.updateValueClan((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValueClan().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "clan")))))));
                        pl.sqlitetaxas.updateValuePrefeitura((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValuePrefeitura().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "pf")))))));
                    }
                }else{
                    //AdminShop
                    e.setTotal(taxValue);
                    pl.sqlitetaxas.updateValueClan((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValueClan().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "clan")))))));
                    pl.sqlitetaxas.updateValuePrefeitura((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValuePrefeitura().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "pf")))))));
                }

            }
        }
    }
    @EventHandler
    public void TaxPreSell(ShopPurchaseEvent e) {
        if (e.getShop().getShopType() == ShopType.SELLING) {
            if (e.getShop().ownerName().equals(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(Objects.requireNonNull(pl.translate().getString("no-tax").replace("&", "§")));
            } else {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat formato = new DecimalFormat("####.##", symbols);
                double taxValue = e.getTotal() + (pl.tax.taxValue(e.getShop().getPrice()) * e.getAmount());
                double taxValueUnique = pl.tax.taxValue(e.getShop().getPrice());

                double balance = pl.ess.getUser(e.getPlayer().getPlayer()).getMoney().doubleValue();
                double taxtotal = taxValueUnique * e.getAmount();
                if (Tax.hasMoney(balance, taxValue)) {
                    for (String msg : pl.translate().getStringList("success-buy")) {
                        e.getPlayer().sendMessage(msg
                                .replace("&", "§")
                                .replace("$tax$", String.valueOf(formato.format(taxValueUnique)))
                                .replace("$quantia$", String.valueOf(e.getAmount()))
                                .replace("$taxtotal$", String.valueOf(formato.format(taxtotal)))
                                .replace("$item$", Util.getItemStackName(e.getShop().getItem()))
                                .replace("$total$", String.valueOf(formato.format(taxValue)))
                        );
                    }
                    e.setTotal(taxValue);
                    pl.ess.getUser(e.getShop().getOwner()).takeMoney(BigDecimal.valueOf(Double.parseDouble(formato.format(taxtotal))));//tira a taxa do dono do shop

                    pl.sqlitetaxas.updateValueClan((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValueClan().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "clan")))))));
                    pl.sqlitetaxas.updateValuePrefeitura((float) Double.parseDouble(formato.format((pl.sqlitetaxas.getValuePrefeitura().floatValue() + Double.parseDouble(formato.format(pl.tax.divideMoney(taxValue, "pf")))))));

                } else {
                    e.setCancelled(true);
                    for (String msg : pl.translate().getStringList("no-have-money-buy")) {
                        e.getPlayer().sendMessage(msg
                                .replace("&", "§")
                                .replace("$tax$", String.valueOf(formato.format(taxValueUnique)))
                                .replace("$total$", String.valueOf(formato.format(taxValue)))
                        );
                    }
                }
            }
        }
    }
}
