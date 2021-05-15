package thornyaplugin.thornyaplugin.utils;

import java.util.concurrent.TimeUnit;
@SuppressWarnings("ALL")
public class TimeFormatter {

    private long time;

    public TimeFormatter(long time) {
        this.time = time;
    }

    public String format() {
        if (time == 0)
            return "0 segundos";

        long dias = TimeUnit.MILLISECONDS.toDays(time);
        long horas = TimeUnit.MILLISECONDS.toHours(time) - (dias * 24);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(time) - (TimeUnit.MILLISECONDS.toHours(time) * 60);
        long segundos = TimeUnit.MILLISECONDS.toSeconds(time) - (TimeUnit.MILLISECONDS.toMinutes(time) * 60);

        StringBuilder sb = new StringBuilder();

        if (dias > 0)
            sb.append(dias + (dias == 1 ? " dia" : " dias"));

        if (horas > 0)
            sb.append(dias > 0 ? (minutos > 0 ? ", " : " e ") : "").append(horas + (horas == 1 ? " hora" : " horas"));

        if (minutos > 0)
            sb.append(dias > 0 || horas > 0 ? (segundos > 0 ? ", " : " e ") : "").append(minutos + (minutos == 1 ? " minuto" : " minutos"));

        if (segundos > 0)
            sb.append(dias > 0 || horas > 0 || minutos > 0 ? " e " : (sb.length() > 0 ? ", " : "")).append(segundos + (segundos == 1 ? " segundo" : " segundos"));

        String s = sb.toString();
        return s.isEmpty() ? "0 segundos" : s;
    }

    public static String format(long time) {
        return new TimeFormatter(time).format();
    }

}
