package th.ac.ku.sci.cs.projectsa.fun;

import th.ac.ku.sci.cs.projectsa.*;
import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: shuffle list
public class MIDIPlayer {
	public static Synthesizer mainSynthesizer = null;
	public static Receiver mainSynthesizer_Receiver = null;
	public static Sequencer mainSequencer = null;
	public static Transmitter mainSequencer_Transmitter = null;
	public static ExecutorService mainThread = null;
	private static boolean doingShutdown = false;

	public static void main() {
		try {
			realMain();
		} catch (Exception e) {
			// Shutdown part
			shutdown();
			System.err.println(Main.clReportHeader("MIDIPlayer", "DEVFUNERR")
					+ "Caught Throwable typed 'Exception', StackTrace is displayed below (no GUI reporting):");
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		if (doingShutdown == false) {
			doingShutdown = true;
			try {
				mainSequencer.stop();
			} catch (Exception e) {
			}
			try {
				mainThread.shutdown();
			} catch (Exception e) {
			}
			try {
				mainSequencer_Transmitter.close();
			} catch (Exception e) {
			}
			try {
				mainSequencer.close();
			} catch (Exception e) {
			}
			try {
				mainSynthesizer_Receiver.close();
			} catch (Exception e) {
			}
			try {
				mainSynthesizer.close();
			} catch (Exception e) {
			}
		}
	}

	public static void realMain() throws Exception {
		boolean isSoundFontLoaded = false;
		String midiFilePrefix = "./misc/fun/midi/songs/";
		String[] soundFontPaths = new String[] { "./misc/fun/midi/sf/2.sf2", "./misc/fun/midi/sf/1.sf2" };
		List<String> midiFiles = new ArrayList<String>(250 + 4 * 2);
		for (Boolean __ : new Boolean[2]) {
			midiFiles.add("rickroll.mid");
			midiFiles.add("mhwgo.mid");
			midiFiles.add("thainame_restorehappinessforthailand.mid");
			midiFiles.add("thainame_love.mid");
		}
		midiFiles.addAll(Arrays.asList(new String[] { "karaokeMIDI/00179.mid", "karaokeMIDI/00218.mid",
				"karaokeMIDI/00683.mid", "karaokeMIDI/00891.mid", "karaokeMIDI/01176.mid", "karaokeMIDI/01723.mid",
				"karaokeMIDI/02096.mid", "karaokeMIDI/02663.mid", "karaokeMIDI/02887.mid", "karaokeMIDI/03751.mid",
				"karaokeMIDI/03767.mid", "karaokeMIDI/03816.mid", "karaokeMIDI/03879.mid", "karaokeMIDI/04002.mid",
				"karaokeMIDI/04612.mid", "karaokeMIDI/10401.mid", "karaokeMIDI/10514.mid", "karaokeMIDI/10982.mid",
				"karaokeMIDI/11261.mid", "karaokeMIDI/11383.mid", "karaokeMIDI/11422.mid", "karaokeMIDI/11868.mid",
				"karaokeMIDI/12033.mid", "karaokeMIDI/12989.mid", "karaokeMIDI/13146.mid", "karaokeMIDI/13306.mid",
				"karaokeMIDI/13901.mid", "karaokeMIDI/13910.mid", "karaokeMIDI/14523.mid", "karaokeMIDI/14668.mid",
				"karaokeMIDI/20194.mid", "karaokeMIDI/20223.mid", "karaokeMIDI/20646.mid", "karaokeMIDI/20797.mid",
				"karaokeMIDI/21094.mid", "karaokeMIDI/21334.mid", "karaokeMIDI/21714.mid", "karaokeMIDI/22057.mid",
				"karaokeMIDI/22163.mid", "karaokeMIDI/22510.mid", "karaokeMIDI/22906.mid", "karaokeMIDI/23038.mid",
				"karaokeMIDI/23314.mid", "karaokeMIDI/24312.mid", "karaokeMIDI/30004.mid", "karaokeMIDI/30165.mid",
				"karaokeMIDI/31251.mid", "karaokeMIDI/31404.mid", "karaokeMIDI/31505.mid", "karaokeMIDI/31525.mid",
				"karaokeMIDI/31802.mid", "karaokeMIDI/32055.mid", "karaokeMIDI/32687.mid", "karaokeMIDI/32872.mid",
				"karaokeMIDI/32980.mid", "karaokeMIDI/33665.mid", "karaokeMIDI/34249.mid", "karaokeMIDI/34262.mid",
				"karaokeMIDI/34268.mid", "karaokeMIDI/34981.mid", "karaokeMIDI/40260.mid", "karaokeMIDI/40374.mid",
				"karaokeMIDI/40407.mid", "karaokeMIDI/41969.mid", "karaokeMIDI/42285.mid", "karaokeMIDI/42615.mid",
				"karaokeMIDI/43095.mid", "karaokeMIDI/43218.mid", "karaokeMIDI/44245.mid", "karaokeMIDI/44552.mid",
				"karaokeMIDI/44790.mid", "karaokeMIDI/50399.mid", "karaokeMIDI/50420.mid", "karaokeMIDI/50900.mid",
				"karaokeMIDI/51055.mid", "karaokeMIDI/51468.mid", "karaokeMIDI/51658.mid", "karaokeMIDI/51675.mid",
				"karaokeMIDI/51695.mid", "karaokeMIDI/51830.mid", "karaokeMIDI/51831.mid", "karaokeMIDI/52480.mid",
				"karaokeMIDI/52594.mid", "karaokeMIDI/52882.mid", "karaokeMIDI/52948.mid", "karaokeMIDI/53157.mid",
				"karaokeMIDI/53302.mid", "karaokeMIDI/53440.mid", "karaokeMIDI/53691.mid", "karaokeMIDI/53980.mid",
				"karaokeMIDI/54352.mid", "karaokeMIDI/54895.mid", "karaokeMIDI/60934.mid", "karaokeMIDI/61177.mid",
				"karaokeMIDI/63105.mid", "karaokeMIDI/63137.mid", "karaokeMIDI/63846.mid", "karaokeMIDI/64465.mid",
				"karaokeMIDI/64973.mid", "karaokeMIDI/70098.mid", "karaokeMIDI/70136.mid", "karaokeMIDI/71684.mid",
				"karaokeMIDI/71726.mid", "karaokeMIDI/72255.mid", "karaokeMIDI/72272.mid", "karaokeMIDI/72473.mid",
				"karaokeMIDI/72567.mid", "karaokeMIDI/72771.mid", "karaokeMIDI/72979.mid", "karaokeMIDI/73949.mid",
				"karaokeMIDI/74023.mid", "karaokeMIDI/74205.mid", "karaokeMIDI/74443.mid", "karaokeMIDI/74816.mid",
				"karaokeMIDI/80836.mid", "karaokeMIDI/81084.mid", "karaokeMIDI/81301.mid", "karaokeMIDI/81510.mid",
				"karaokeMIDI/81533.mid", "karaokeMIDI/81626.mid", "karaokeMIDI/81851.mid", "karaokeMIDI/82079.mid",
				"karaokeMIDI/84195.mid", "karaokeMIDI/84308.mid", "karaokeMIDI/84351.mid", "karaokeMIDI/90029.mid",
				"karaokeMIDI/90208.mid", "karaokeMIDI/90762.mid", "karaokeMIDI/90837.mid", "karaokeMIDI/91137.mid",
				"karaokeMIDI/91526.mid", "karaokeMIDI/92065.mid", "karaokeMIDI/92358.mid", "karaokeMIDI/92529.mid",
				"karaokeMIDI/92576.mid", "karaokeMIDI/93283.mid", "karaokeMIDI/93287.mid", "karaokeMIDI/93327.mid",
				"karaokeMIDI/A0117.mid", "karaokeMIDI/A0371.mid", "karaokeMIDI/A0422.mid", "karaokeMIDI/A0503.mid",
				"karaokeMIDI/A0527.mid", "karaokeMIDI/A0578.mid", "karaokeMIDI/A0617.mid", "karaokeMIDI/A0780.mid",
				"karaokeMIDI/A01575.mid", "karaokeMIDI/A01624.mid", "karaokeMIDI/A01997.mid",
				"karaokeMIDI/A02873.mid", "karaokeMIDI/A03276.mid", "karaokeMIDI/A03370.mid",
				"karaokeMIDI/A03801.mid", "karaokeMIDI/A04216.mid", "karaokeMIDI/A04463.mid",
				"karaokeMIDI/A04489.mid", "karaokeMIDI/A05029.mid", "karaokeMIDI/A05831.mid",
				"karaokeMIDI/A05968.mid", "karaokeMIDI/A07088.mid", "karaokeMIDI/A07528.mid",
				"karaokeMIDI/A08608.mid", "karaokeMIDI/A08815.mid", "karaokeMIDI/A08976.mid",
				"karaokeMIDI/A09101.mid", "karaokeMIDI/A09124.mid", "karaokeMIDI/A09191.mid",
				"karaokeMIDI/A09346.mid", "karaokeMIDI/A09501.mid", "karaokeMIDI/A09535.mid",
				"karaokeMIDI/A09565.mid", "karaokeMIDI/A09715.mid", "karaokeMIDI/A09736.mid",
				"karaokeMIDI/A010110.mid", "karaokeMIDI/A010351.mid", "karaokeMIDI/A010873.mid",
				"karaokeMIDI/A011040.mid", "karaokeMIDI/A011742.mid", "karaokeMIDI/A011767.mid",
				"karaokeMIDI/A012148.mid", "karaokeMIDI/A012218.mid", "karaokeMIDI/A012515.mid",
				"karaokeMIDI/A012577.mid", "karaokeMIDI/A013705.mid", "karaokeMIDI/A013710.mid",
				"karaokeMIDI/A014061.mid", "karaokeMIDI/A015337.mid", "karaokeMIDI/A015361.mid",
				"karaokeMIDI/A017261.mid", "karaokeMIDI/A017615.mid", "karaokeMIDI/A017681.mid",
				"karaokeMIDI/A018096.mid", "karaokeMIDI/A018207.mid", "karaokeMIDI/A018220.mid",
				"karaokeMIDI/A018669.mid", "karaokeMIDI/A019482.mid", "karaokeMIDI/A020506.mid",
				"karaokeMIDI/A020698.mid", "karaokeMIDI/A020944.mid", "karaokeMIDI/A021234.mid",
				"karaokeMIDI/A021958.mid", "karaokeMIDI/A021990.mid", "karaokeMIDI/A022146.mid",
				"karaokeMIDI/A022159.mid", "karaokeMIDI/A022600.mid", "karaokeMIDI/A022681.mid",
				"karaokeMIDI/A022716.mid", "karaokeMIDI/A023645.mid", "karaokeMIDI/A024429.mid",
				"karaokeMIDI/A024597.mid", "karaokeMIDI/A024815.mid", "karaokeMIDI/A024898.mid",
				"karaokeMIDI/A025061.mid", "karaokeMIDI/A025194.mid", "karaokeMIDI/A025410.mid",
				"karaokeMIDI/A025447.mid", "karaokeMIDI/A025640.mid", "karaokeMIDI/B00358.mid",
				"karaokeMIDI/B001597.mid", "karaokeMIDI/B001643.mid", "karaokeMIDI/B002029.mid",
				"karaokeMIDI/B003268.mid", "karaokeMIDI/B003620.mid", "karaokeMIDI/B003660.mid",
				"karaokeMIDI/B004651.mid", "karaokeMIDI/B004943.mid", "karaokeMIDI/B006001.mid",
				"karaokeMIDI/B006765.mid", "karaokeMIDI/B006860.mid", "karaokeMIDI/B007231.mid",
				"karaokeMIDI/B007261.mid", "karaokeMIDI/B007607.mid", "karaokeMIDI/B008004.mid",
				"karaokeMIDI/B009741.mid", "karaokeMIDI/B0010103.mid", "karaokeMIDI/B0010106.mid",
				"karaokeMIDI/B0010526.mid", "karaokeMIDI/B0010676.mid", "karaokeMIDI/B0010926.mid",
				"karaokeMIDI/B0011025.mid", "karaokeMIDI/B0011329.mid", "karaokeMIDI/B0011456.mid",
				"karaokeMIDI/B0011576.mid", "karaokeMIDI/B0013641.mid", "karaokeMIDI/B0014595.mid",
				"karaokeMIDI/B0014657.mid", "karaokeMIDI/B0015181.mid", "karaokeMIDI/B0015300.mid",
				"karaokeMIDI/B0015735.mid", "karaokeMIDI/B0016575.mid" }));

		try {
			mainSequencer = MidiSystem.getSequencer();
			mainSequencer.open();
		} catch (MidiUnavailableException e) {
			throw e;
		}

		Soundbank[] soundbanks = new Soundbank[soundFontPaths.length];
		for (int i = 0; i < soundFontPaths.length; i++) {
			try {
				soundbanks[i] = MidiSystem.getSoundbank(new java.io.File(soundFontPaths[i]));
				isSoundFontLoaded = true;
			} catch (InvalidMidiDataException | java.io.IOException e) {
				System.err.println(Main.clReportHeader("MIDIPlayer", "DEVFUNERR")
						+ "Cannot load this soundfont (" + (soundFontPaths[i]) + "). See below for StackTrace:");
				e.printStackTrace();

				continue;
			}
		}

		// disable loading of external soundfont because ITDONTWORKKKKKKKKKK
		if (isSoundFontLoaded && false)

		{
			mainSynthesizer = MidiSystem.getSynthesizer();
			for (Soundbank soundbank : soundbanks) {
				if (soundbank != null) {
					mainSynthesizer.loadAllInstruments(soundbank);
				}
			}
			mainSequencer_Transmitter = mainSequencer.getTransmitter();
			mainSynthesizer_Receiver = mainSynthesizer.getReceiver();
			mainSequencer_Transmitter.setReceiver(mainSynthesizer_Receiver);
		} else {
			System.out.println(Main.clReportHeader("MIDIPlayer", "DEVFUN")
					+ "Could not load the specified SoundFont. Using the built-in SoundFont.");
		}

		ExecutorService mainThread = Executors.newSingleThreadExecutor();
		mainThread.submit(() -> {
			boolean specialFuncExitCaseA = false;
			try {
				System.out.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUNDEBUG")
						+ "Main thread has started.");
				boolean havePassedOneRound = false;
				boolean havePlayAnySong = false;
				while (true) {
					Collections.shuffle(midiFiles);
					for (String midiFile : midiFiles) {
						try {
							mainSequencer
									.setSequence(MidiSystem.getSequence(new java.io.File(midiFilePrefix + midiFile)));
							mainSequencer.start();
							while (mainSequencer.isRunning()) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									System.out.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>",
											"DEVFUNNOTICE")
											+ "Received InterruptedException in main thread of midi-playing, stop playback... See below for StackTrace:");
									e1.printStackTrace();
									specialFuncExitCaseA = true;
									throw e1;
								}
							}
							if (doingShutdown) {
								break;
							}
							havePlayAnySong = true;
						} catch (InvalidMidiDataException | java.io.IOException e) {
							if (havePassedOneRound == false) {
								System.err.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUNERR")
										+ "Cannot play this song (" + (midiFilePrefix + midiFile)
										+ "). See below for StackTrace:");
								e.printStackTrace();
							}
						}
					}
					if (doingShutdown) {
						break;
					}
					havePassedOneRound = true;
					if (havePlayAnySong) {
					} else {
						System.err.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUN")
								+ "No songs able to play T_T");
						break;
					}
				}
			} catch (Exception e0) {
				if (specialFuncExitCaseA) {
				} else {
					System.err.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUNERR")
							+ "Caught Throwable typed 'Exception' in main thread of midi-playing, StackTrace is displayed below (no GUI reporting):");
					e0.printStackTrace();
				}
			} finally {
				try {
					shutdown();
				} catch (Throwable e0) {
					System.err.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUNERR")
							+ "Shutdown MIDI-Playing system has exception. Stacktrace is below:");
					e0.printStackTrace();
				}
				System.out.println(Main.clReportHeader("MIDIPlayer:<mainThread-function>", "DEVFUNDEBUG")
						+ "Main thread has finished.");
			}
		});

	}
}
