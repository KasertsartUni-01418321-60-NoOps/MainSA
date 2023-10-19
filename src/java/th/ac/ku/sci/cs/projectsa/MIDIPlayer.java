package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

// TODO: ยัดเพลงเยอะหน่อย
public class MIDIPlayer {
	private static java.util.List<String> playlist = new java.util.ArrayList<>();
	private static short currentIndex = 0;
	private static javax.sound.midi.Sequencer sequencer = null;
	private static java.io.InputStream midiDataStream = null;
	public static boolean isStop = false;

	// entire exception handling info: mode=no
	// it's init func lamo
	public static void main() {
		MIDIPlayer.playlist.clear();
		MIDIPlayer.playlist = new java.util.ArrayList<>();
		MIDIPlayer.currentIndex = 0;
		MIDIPlayer.isStop = false;
		try {
			MIDIPlayer.sequencer = javax.sound.midi.MidiSystem.getSequencer();
			MIDIPlayer.sequencer.open();
		} catch (javax.sound.midi.MidiUnavailableException e) {
			try {
				MIDIPlayer.sequencer.close();
			} catch (NullPointerException e1) {
			}
		}
		String[] tmp1 = new String[] { "karaokeMIDI/A021385.mid", "karaokeMIDI/B00255.mid", "karaokeMIDI/52929.mid",
				"karaokeMIDI/A012890.mid", "karaokeMIDI/54794.mid", "karaokeMIDI/91341.mid", "karaokeMIDI/B005986.mid",
				"karaokeMIDI/00968.mid", "karaokeMIDI/B003257.mid", "karaokeMIDI/B007117.mid", "karaokeMIDI/83440.mid",
				"karaokeMIDI/99307.mid", "karaokeMIDI/11647.mid", "karaokeMIDI/A02204.mid", "karaokeMIDI/A013158.mid",
				"karaokeMIDI/80872.mid", "karaokeMIDI/51103.mid", "karaokeMIDI/42777.mid", "karaokeMIDI/A0600.mid",
				"karaokeMIDI/70795.mid", "karaokeMIDI/A013493.mid", "karaokeMIDI/03468.mid", "karaokeMIDI/63189.mid",
				"karaokeMIDI/B007204.mid", "karaokeMIDI/82066.mid", "karaokeMIDI/53165.mid", "karaokeMIDI/62820.mid",
				"karaokeMIDI/43543.mid", "karaokeMIDI/83177.mid", "karaokeMIDI/62908.mid", "karaokeMIDI/A013695.mid",
				"karaokeMIDI/B007002.mid", "karaokeMIDI/B0014309.mid", "karaokeMIDI/A023168.mid",
				"karaokeMIDI/02033.mid", "karaokeMIDI/24204.mid", "karaokeMIDI/99289.mid", "karaokeMIDI/A014504.mid",
				"karaokeMIDI/A023081.mid", "karaokeMIDI/93842.mid", "karaokeMIDI/A022161.mid", "karaokeMIDI/41351.mid",
				"karaokeMIDI/10577.mid", "karaokeMIDI/61968.mid", "karaokeMIDI/B009531.mid", "karaokeMIDI/64293.mid",
				"karaokeMIDI/82143.mid", "karaokeMIDI/A015740.mid", "karaokeMIDI/B00624.mid", "karaokeMIDI/12191.mid",
				"karaokeMIDI/91668.mid", "karaokeMIDI/53715.mid", "karaokeMIDI/90570.mid", "karaokeMIDI/11747.mid",
				"karaokeMIDI/52457.mid", "karaokeMIDI/84150.mid", "karaokeMIDI/03053.mid", "karaokeMIDI/99395.mid",
				"karaokeMIDI/42479.mid", "karaokeMIDI/33143.mid", "karaokeMIDI/A03410.mid", "karaokeMIDI/B003421.mid",
				"karaokeMIDI/B005072.mid", "karaokeMIDI/71959.mid", "karaokeMIDI/A013149.mid", "karaokeMIDI/34749.mid",
				"karaokeMIDI/B0014965.mid", "karaokeMIDI/A022757.mid", "karaokeMIDI/01142.mid",
				"karaokeMIDI/A06013.mid", "karaokeMIDI/A025542.mid", "karaokeMIDI/34812.mid", "karaokeMIDI/33739.mid",
				"karaokeMIDI/91230.mid", "karaokeMIDI/13620.mid", "karaokeMIDI/B0010236.mid", "karaokeMIDI/A024100.mid",
				"karaokeMIDI/A03515.mid", "karaokeMIDI/A09111.mid", "karaokeMIDI/B003283.mid",
				"karaokeMIDI/B005994.mid", "karaokeMIDI/80925.mid", "karaokeMIDI/80217.mid", "karaokeMIDI/A05032.mid",
				"karaokeMIDI/B0010850.mid", "karaokeMIDI/90248.mid", "karaokeMIDI/A013468.mid", "karaokeMIDI/A0278.mid",
				"karaokeMIDI/A010037.mid", "karaokeMIDI/81502.mid", "karaokeMIDI/73173.mid", "karaokeMIDI/A0499.mid",
				"karaokeMIDI/A018763.mid", "karaokeMIDI/43552.mid", "karaokeMIDI/51569.mid", "karaokeMIDI/00032.mid",
				"karaokeMIDI/01816.mid", "karaokeMIDI/50895.mid", "karaokeMIDI/A015794.mid", "karaokeMIDI/A024616.mid",
				"karaokeMIDI/04119.mid", "karaokeMIDI/13164.mid", "karaokeMIDI/41962.mid", "karaokeMIDI/63554.mid",
				"karaokeMIDI/13654.mid", "karaokeMIDI/01772.mid", "karaokeMIDI/30657.mid", "karaokeMIDI/A014285.mid",
				"karaokeMIDI/43473.mid", "karaokeMIDI/81316.mid", "karaokeMIDI/A022556.mid", "karaokeMIDI/31927.mid",
				"karaokeMIDI/74698.mid", "karaokeMIDI/60238.mid", "karaokeMIDI/A018331.mid", "karaokeMIDI/52021.mid",
				"karaokeMIDI/83182.mid", "karaokeMIDI/B0013492.mid", "karaokeMIDI/A05562.mid", "karaokeMIDI/81398.mid",
				"karaokeMIDI/B0011252.mid", "karaokeMIDI/10957.mid", "karaokeMIDI/A015332.mid", "karaokeMIDI/80288.mid",
				"karaokeMIDI/A015351.mid", "karaokeMIDI/41400.mid", "karaokeMIDI/A02792.mid", "karaokeMIDI/30716.mid",
				"karaokeMIDI/B009232.mid", "karaokeMIDI/60589.mid", "karaokeMIDI/B008382.mid",
				"karaokeMIDI/B0012995.mid", "karaokeMIDI/B006344.mid", "karaokeMIDI/23246.mid",
				"karaokeMIDI/A09200.mid", "karaokeMIDI/A024595.mid", "karaokeMIDI/61219.mid", "karaokeMIDI/73621.mid",
				"karaokeMIDI/A010966.mid", "karaokeMIDI/80396.mid", "karaokeMIDI/A05615.mid", "karaokeMIDI/B004754.mid",
				"karaokeMIDI/B007588.mid", "karaokeMIDI/B00216.mid", "karaokeMIDI/24419.mid", "karaokeMIDI/A016631.mid",
				"karaokeMIDI/A019707.mid", "karaokeMIDI/A010448.mid", "karaokeMIDI/74219.mid", "karaokeMIDI/23400.mid",
				"karaokeMIDI/B003895.mid", "karaokeMIDI/41321.mid", "karaokeMIDI/51833.mid", "karaokeMIDI/A022945.mid",
				"karaokeMIDI/A025576.mid", "karaokeMIDI/01813.mid", "karaokeMIDI/43688.mid", "karaokeMIDI/04074.mid",
				"karaokeMIDI/B006753.mid", "karaokeMIDI/11599.mid", "karaokeMIDI/A018008.mid",
				"karaokeMIDI/B0016758.mid", "karaokeMIDI/42763.mid", "karaokeMIDI/03320.mid", "karaokeMIDI/41976.mid",
				"karaokeMIDI/A0668.mid", "karaokeMIDI/B0012031.mid", "karaokeMIDI/B0015555.mid",
				"karaokeMIDI/B0016908.mid", "karaokeMIDI/A07993.mid", "karaokeMIDI/B003958.mid",
				"karaokeMIDI/41229.mid", "karaokeMIDI/A08530.mid", "karaokeMIDI/64369.mid", "karaokeMIDI/B0012620.mid",
				"karaokeMIDI/B0013643.mid", "karaokeMIDI/NYK628.mid", "karaokeMIDI/A07404.mid",
				"karaokeMIDI/A02706.mid", "karaokeMIDI/42152.mid", "karaokeMIDI/13763.mid", "karaokeMIDI/83609.mid",
				"karaokeMIDI/51666.mid", "karaokeMIDI/14528.mid", "karaokeMIDI/80864.mid", "karaokeMIDI/34230.mid",
				"karaokeMIDI/43279.mid", "karaokeMIDI/03785.mid", "karaokeMIDI/70446.mid", "karaokeMIDI/10192.mid",
				"karaokeMIDI/B001908.mid", "karaokeMIDI/14526.mid", "karaokeMIDI/A08470.mid", "karaokeMIDI/A019897.mid",
				"karaokeMIDI/82304.mid", "karaokeMIDI/NYK815.mid", "karaokeMIDI/B006277.mid", "karaokeMIDI/40505.mid",
				"karaokeMIDI/73080.mid", "karaokeMIDI/71697.mid", "karaokeMIDI/21670.mid", "karaokeMIDI/93611.mid",
				"karaokeMIDI/B004970.mid", "karaokeMIDI/04424.mid", "karaokeMIDI/34474.mid", "karaokeMIDI/31844.mid",
				"karaokeMIDI/64723.mid", "karaokeMIDI/B007584.mid", "karaokeMIDI/00059.mid", "karaokeMIDI/30220.mid",
				"karaokeMIDI/62305.mid", "karaokeMIDI/33305.mid", "karaokeMIDI/24716.mid", "karaokeMIDI/A013543.mid",
				"karaokeMIDI/51909.mid", "karaokeMIDI/90672.mid", "karaokeMIDI/62998.mid", "karaokeMIDI/32183.mid",
				"karaokeMIDI/A020262.mid", "karaokeMIDI/60669.mid", "karaokeMIDI/43865.mid", "karaokeMIDI/10334.mid",
				"karaokeMIDI/64200.mid", "karaokeMIDI/63203.mid", "karaokeMIDI/12980.mid", "karaokeMIDI/91047.mid",
				"karaokeMIDI/A01638.mid", "karaokeMIDI/60823.mid", "karaokeMIDI/A022218.mid", "karaokeMIDI/B004202.mid",
				"karaokeMIDI/A04956.mid", "karaokeMIDI/B0015776.mid", "karaokeMIDI/B008171.mid",
				"karaokeMIDI/B0010626.mid", "karaokeMIDI/30592.mid", "karaokeMIDI/A020341.mid", "karaokeMIDI/02965.mid",
				"karaokeMIDI/A011194.mid", "karaokeMIDI/74880.mid", "karaokeMIDI/A08044.mid",
				"karaokeMIDI/B0012588.mid", "karaokeMIDI/83510.mid", "karaokeMIDI/54585.mid", "karaokeMIDI/A012071.mid",
				"karaokeMIDI/B003711.mid", "karaokeMIDI/33163.mid", "karaokeMIDI/44239.mid", "karaokeMIDI/00583.mid",
				"karaokeMIDI/B001930.mid", "karaokeMIDI/73637.mid" };
		for (boolean tmp2 : new boolean[] { false, true }) {
			MIDIPlayer.playlist.add("rickroll.mid");
			MIDIPlayer.playlist.add("mhwgo.mid");
			MIDIPlayer.playlist.add("thainame_restorehappinessforthailand.mid");
			MIDIPlayer.playlist.add("thainame_love.mid");
		}
		MIDIPlayer.playlist.addAll(java.util.Arrays.asList(tmp1));

		java.util.Collections.shuffle(MIDIPlayer.playlist);
		MIDIPlayer.sequencer.addMetaEventListener(meta -> {
			if (meta.getType() == 0x2F) {
				if (MIDIPlayer.isStop) {
				} else {
					MIDIPlayer.currentIndex++;
					MIDIPlayer.play(); // Play the next song
				}

			}
		});
	}

	// entire exception handling info: mode=no
	public static void play() {
		while (true) {
			MIDIPlayer.isStop = false;
			if (MIDIPlayer.currentIndex >= MIDIPlayer.playlist.size()) {
				MIDIPlayer.currentIndex = 0;
				java.util.Collections.shuffle(MIDIPlayer.playlist);
			}
			String currentSongName = MIDIPlayer.playlist.get(MIDIPlayer.currentIndex);
			// do not put leading slash for jarfile resource for this line of code lamo
			MIDIPlayer.midiDataStream = MIDIPlayer.class.getClassLoader()
					.getResourceAsStream("resources/" + currentSongName);
			try {
				MIDIPlayer.sequencer.setSequence(
						javax.sound.midi.MidiSystem.getSequence(MIDIPlayer.midiDataStream));
			} catch (java.io.EOFException e) {
				currentIndex++;
				continue;
			} catch (javax.sound.midi.InvalidMidiDataException | java.io.IOException e) {
				MIDIPlayer.sequencer.close();
				return;
			} finally {
				try {
					MIDIPlayer.midiDataStream.close();
				} catch (java.io.IOException e1) {
				}
			}
			break;
		}
		MIDIPlayer.sequencer.start();

	}

	// entire exception handling info: mode=no
	public static void stop() {

		MIDIPlayer.isStop = true;
		MIDIPlayer.sequencer.stop();
		MIDIPlayer.sequencer.close();

	}
}
