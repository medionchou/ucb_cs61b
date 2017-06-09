import synthesizer.GuitarString;

/**
 * Created by medionchou on 2017/5/25.
 */
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        synthesizer.GuitarString[] stringN = new synthesizer.GuitarString[keyboard.length()];

        for (int i = 0; i < keyboard.length(); i++) {
            stringN[i] = new GuitarString(440 * Math.pow(2, (i - 24) / 12.0));
        }


        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();

                int idx = keyboard.indexOf(key);

                if (idx >= 0) {
                    stringN[idx].pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < keyboard.length(); i++)
                sample += stringN[i].sample();

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */

            for (int i = 0; i < keyboard.length(); i++)
                stringN[i].tic();
        }
    }
}
