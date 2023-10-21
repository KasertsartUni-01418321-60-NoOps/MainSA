package th.ac.ku.sci.cs.projectsa.fun;

import th.ac.ku.sci.cs.projectsa.*;
import javax.sound.midi.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		List<String> soundFontPaths = listFilesInDirectory("./misc/fun/midi/sf/");
		List<String> midiFiles =  listFilesInDirectory(midiFilePrefix);

		try {
			mainSequencer = MidiSystem.getSequencer();
			mainSequencer.open();
		} catch (MidiUnavailableException e) {
			throw e;
		}

		Soundbank[] soundbanks = new Soundbank[soundFontPaths.size()];
		for (int i = 0; i < soundFontPaths.size(); i++) {
			try {
				soundbanks[i] = MidiSystem.getSoundbank(new java.io.File(soundFontPaths.get(i)));
				isSoundFontLoaded = true;
			} catch (InvalidMidiDataException | java.io.IOException e) {
				System.err.println(Main.clReportHeader("MIDIPlayer", "DEVFUNERR")
						+ "Cannot load this soundfont (" + (soundFontPaths.get(i)) + "). See below for StackTrace:");
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
					+ "Could not load the any external SoundFont. Using the built-in SoundFont.");
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

	private static List<String> listFilesInDirectory(String directoryPath) throws MyExceptionHandling.UserException {
		List<String> fileNames = new ArrayList<>();
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				String fileName = file.getName();
				fileNames.add(fileName);
			}
		} else {
			throw new MyExceptionHandling.UserException("Unknown error on listing file inside directory");
		}
		return fileNames;
	}
}
