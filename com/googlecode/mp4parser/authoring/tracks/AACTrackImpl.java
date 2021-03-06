package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SoundMediaHeaderBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.TimeToSampleBox.Entry;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.AudioSpecificConfig;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.SLConfigDescriptor;
import com.googlecode.mp4parser.util.ChannelHelper;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AACTrackImpl extends AbstractTrack {
    static Map<Integer, String> audioObjectTypes;
    public static Map<Integer, Integer> samplingFrequencyIndexMap;
    long avgBitRate;
    int bufferSizeDB;
    AdtsHeader firstHeader;
    private String lang;
    long maxBitRate;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    List<Entry> stts;
    TrackMetaData trackMetaData;

    class AdtsHeader {
        int bufferFullness;
        int channelconfig;
        int copyrightStart;
        int copyrightedStream;
        int frameLength;
        int home;
        int layer;
        int mpegVersion;
        int numAacFramesPerAdtsFrame;
        int original;
        int profile;
        int protectionAbsent;
        int sampleFrequencyIndex;
        int sampleRate;

        AdtsHeader() {
        }

        int getSize() {
            return (this.protectionAbsent == 0 ? 2 : 0) + 7;
        }
    }

    static {
        audioObjectTypes = new HashMap();
        audioObjectTypes.put(Integer.valueOf(1), "AAC Main");
        audioObjectTypes.put(Integer.valueOf(2), "AAC LC (Low Complexity)");
        audioObjectTypes.put(Integer.valueOf(3), "AAC SSR (Scalable Sample Rate)");
        audioObjectTypes.put(Integer.valueOf(4), "AAC LTP (Long Term Prediction)");
        audioObjectTypes.put(Integer.valueOf(5), "SBR (Spectral Band Replication)");
        audioObjectTypes.put(Integer.valueOf(6), "AAC Scalable");
        audioObjectTypes.put(Integer.valueOf(7), "TwinVQ");
        audioObjectTypes.put(Integer.valueOf(8), "CELP (Code Excited Linear Prediction)");
        audioObjectTypes.put(Integer.valueOf(9), "HXVC (Harmonic Vector eXcitation Coding)");
        audioObjectTypes.put(Integer.valueOf(10), "Reserved");
        audioObjectTypes.put(Integer.valueOf(11), "Reserved");
        audioObjectTypes.put(Integer.valueOf(12), "TTSI (Text-To-Speech Interface)");
        audioObjectTypes.put(Integer.valueOf(13), "Main Synthesis");
        audioObjectTypes.put(Integer.valueOf(14), "Wavetable Synthesis");
        audioObjectTypes.put(Integer.valueOf(15), "General MIDI");
        audioObjectTypes.put(Integer.valueOf(16), "Algorithmic Synthesis and Audio Effects");
        audioObjectTypes.put(Integer.valueOf(17), "ER (Error Resilient) AAC LC");
        audioObjectTypes.put(Integer.valueOf(18), "Reserved");
        audioObjectTypes.put(Integer.valueOf(19), "ER AAC LTP");
        audioObjectTypes.put(Integer.valueOf(20), "ER AAC Scalable");
        audioObjectTypes.put(Integer.valueOf(21), "ER TwinVQ");
        audioObjectTypes.put(Integer.valueOf(22), "ER BSAC (Bit-Sliced Arithmetic Coding)");
        audioObjectTypes.put(Integer.valueOf(23), "ER AAC LD (Low Delay)");
        audioObjectTypes.put(Integer.valueOf(24), "ER CELP");
        audioObjectTypes.put(Integer.valueOf(25), "ER HVXC");
        audioObjectTypes.put(Integer.valueOf(26), "ER HILN (Harmonic and Individual Lines plus Noise)");
        audioObjectTypes.put(Integer.valueOf(27), "ER Parametric");
        audioObjectTypes.put(Integer.valueOf(28), "SSC (SinuSoidal Coding)");
        audioObjectTypes.put(Integer.valueOf(29), "PS (Parametric Stereo)");
        audioObjectTypes.put(Integer.valueOf(30), "MPEG Surround");
        audioObjectTypes.put(Integer.valueOf(31), "(Escape value)");
        audioObjectTypes.put(Integer.valueOf(32), "Layer-1");
        audioObjectTypes.put(Integer.valueOf(33), "Layer-2");
        audioObjectTypes.put(Integer.valueOf(34), "Layer-3");
        audioObjectTypes.put(Integer.valueOf(35), "DST (Direct Stream Transfer)");
        audioObjectTypes.put(Integer.valueOf(36), "ALS (Audio Lossless)");
        audioObjectTypes.put(Integer.valueOf(37), "SLS (Scalable LosslesS)");
        audioObjectTypes.put(Integer.valueOf(38), "SLS non-core");
        audioObjectTypes.put(Integer.valueOf(39), "ER AAC ELD (Enhanced Low Delay)");
        audioObjectTypes.put(Integer.valueOf(40), "SMR (Symbolic Music Representation) Simple");
        audioObjectTypes.put(Integer.valueOf(41), "SMR Main");
        audioObjectTypes.put(Integer.valueOf(42), "USAC (Unified Speech and Audio Coding) (no SBR)");
        audioObjectTypes.put(Integer.valueOf(43), "SAOC (Spatial Audio Object Coding)");
        audioObjectTypes.put(Integer.valueOf(44), "LD MPEG Surround");
        audioObjectTypes.put(Integer.valueOf(45), "USAC");
        samplingFrequencyIndexMap = new HashMap();
        samplingFrequencyIndexMap.put(Integer.valueOf(96000), Integer.valueOf(0));
        samplingFrequencyIndexMap.put(Integer.valueOf(88200), Integer.valueOf(1));
        samplingFrequencyIndexMap.put(Integer.valueOf(64000), Integer.valueOf(2));
        samplingFrequencyIndexMap.put(Integer.valueOf(48000), Integer.valueOf(3));
        samplingFrequencyIndexMap.put(Integer.valueOf(44100), Integer.valueOf(4));
        samplingFrequencyIndexMap.put(Integer.valueOf(32000), Integer.valueOf(5));
        samplingFrequencyIndexMap.put(Integer.valueOf(24000), Integer.valueOf(6));
        samplingFrequencyIndexMap.put(Integer.valueOf(22050), Integer.valueOf(7));
        samplingFrequencyIndexMap.put(Integer.valueOf(16000), Integer.valueOf(8));
        samplingFrequencyIndexMap.put(Integer.valueOf(12000), Integer.valueOf(9));
        samplingFrequencyIndexMap.put(Integer.valueOf(11025), Integer.valueOf(10));
        samplingFrequencyIndexMap.put(Integer.valueOf(8000), Integer.valueOf(11));
        samplingFrequencyIndexMap.put(Integer.valueOf(0), Integer.valueOf(96000));
        samplingFrequencyIndexMap.put(Integer.valueOf(1), Integer.valueOf(88200));
        samplingFrequencyIndexMap.put(Integer.valueOf(2), Integer.valueOf(64000));
        samplingFrequencyIndexMap.put(Integer.valueOf(3), Integer.valueOf(48000));
        samplingFrequencyIndexMap.put(Integer.valueOf(4), Integer.valueOf(44100));
        samplingFrequencyIndexMap.put(Integer.valueOf(5), Integer.valueOf(32000));
        samplingFrequencyIndexMap.put(Integer.valueOf(6), Integer.valueOf(24000));
        samplingFrequencyIndexMap.put(Integer.valueOf(7), Integer.valueOf(22050));
        samplingFrequencyIndexMap.put(Integer.valueOf(8), Integer.valueOf(16000));
        samplingFrequencyIndexMap.put(Integer.valueOf(9), Integer.valueOf(12000));
        samplingFrequencyIndexMap.put(Integer.valueOf(10), Integer.valueOf(11025));
        samplingFrequencyIndexMap.put(Integer.valueOf(11), Integer.valueOf(8000));
    }

    public AACTrackImpl(ReadableByteChannel channel, String lang) throws IOException {
        this.trackMetaData = new TrackMetaData();
        this.lang = "und";
        this.lang = lang;
        parse(channel);
    }

    public AACTrackImpl(ReadableByteChannel channel) throws IOException {
        this.trackMetaData = new TrackMetaData();
        this.lang = "und";
        parse(channel);
    }

    private void parse(ReadableByteChannel channel) throws IOException {
        this.stts = new LinkedList();
        this.samples = new LinkedList();
        this.firstHeader = readSamples(channel);
        double packetsPerSecond = ((double) this.firstHeader.sampleRate) / 1024.0d;
        double duration = ((double) this.samples.size()) / packetsPerSecond;
        long dataSize = 0;
        LinkedList<Integer> queue = new LinkedList();
        for (Sample sample : this.samples) {
            int size = (int) sample.remaining();
            dataSize += (long) size;
            queue.add(Integer.valueOf(size));
            while (true) {
                if (((double) queue.size()) <= packetsPerSecond) {
                    break;
                }
                queue.pop();
            }
            if (queue.size() == ((int) packetsPerSecond)) {
                int currSize = 0;
                Iterator it = queue.iterator();
                while (it.hasNext()) {
                    currSize += ((Integer) it.next()).intValue();
                }
                double d = (double) currSize;
                double currBitrate = ((8.0d * r0) / ((double) queue.size())) * packetsPerSecond;
                if (currBitrate > ((double) this.maxBitRate)) {
                    this.maxBitRate = (long) ((int) currBitrate);
                }
            }
        }
        this.avgBitRate = (long) ((int) (((double) (8 * dataSize)) / duration));
        this.bufferSizeDB = 1536;
        this.sampleDescriptionBox = new SampleDescriptionBox();
        AudioSampleEntry audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE3);
        audioSampleEntry.setChannelCount(2);
        audioSampleEntry.setSampleRate((long) this.firstHeader.sampleRate);
        audioSampleEntry.setDataReferenceIndex(1);
        audioSampleEntry.setSampleSize(16);
        ESDescriptorBox esds = new ESDescriptorBox();
        ESDescriptor descriptor = new ESDescriptor();
        descriptor.setEsId(0);
        SLConfigDescriptor slConfigDescriptor = new SLConfigDescriptor();
        slConfigDescriptor.setPredefined(2);
        descriptor.setSlConfigDescriptor(slConfigDescriptor);
        DecoderConfigDescriptor decoderConfigDescriptor = new DecoderConfigDescriptor();
        decoderConfigDescriptor.setObjectTypeIndication(64);
        decoderConfigDescriptor.setStreamType(5);
        decoderConfigDescriptor.setBufferSizeDB(this.bufferSizeDB);
        decoderConfigDescriptor.setMaxBitRate(this.maxBitRate);
        decoderConfigDescriptor.setAvgBitRate(this.avgBitRate);
        AudioSpecificConfig audioSpecificConfig = new AudioSpecificConfig();
        audioSpecificConfig.setAudioObjectType(2);
        audioSpecificConfig.setSamplingFrequencyIndex(this.firstHeader.sampleFrequencyIndex);
        audioSpecificConfig.setChannelConfiguration(this.firstHeader.channelconfig);
        decoderConfigDescriptor.setAudioSpecificInfo(audioSpecificConfig);
        descriptor.setDecoderConfigDescriptor(decoderConfigDescriptor);
        esds.setData(descriptor.serialize());
        audioSampleEntry.addBox(esds);
        this.sampleDescriptionBox.addBox(audioSampleEntry);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(this.lang);
        this.trackMetaData.setVolume(1.0f);
        this.trackMetaData.setTimescale((long) this.firstHeader.sampleRate);
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public List<Entry> getDecodingTimeEntries() {
        return this.stts;
    }

    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return null;
    }

    public long[] getSyncSamples() {
        return null;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return null;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "soun";
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public Box getMediaHeaderBox() {
        return new SoundMediaHeaderBox();
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return null;
    }

    private AdtsHeader readADTSHeader(ReadableByteChannel channel) throws IOException {
        AdtsHeader hdr = new AdtsHeader();
        ByteBuffer bb = ByteBuffer.allocate(7);
        try {
            ChannelHelper.readFully(channel, bb);
            BitReaderBuffer brb = new BitReaderBuffer((ByteBuffer) bb.rewind());
            if (brb.readBits(12) != 4095) {
                throw new IOException("Expected Start Word 0xfff");
            }
            hdr.mpegVersion = brb.readBits(1);
            hdr.layer = brb.readBits(2);
            hdr.protectionAbsent = brb.readBits(1);
            hdr.profile = brb.readBits(2) + 1;
            hdr.sampleFrequencyIndex = brb.readBits(4);
            hdr.sampleRate = ((Integer) samplingFrequencyIndexMap.get(Integer.valueOf(hdr.sampleFrequencyIndex))).intValue();
            brb.readBits(1);
            hdr.channelconfig = brb.readBits(3);
            hdr.original = brb.readBits(1);
            hdr.home = brb.readBits(1);
            hdr.copyrightedStream = brb.readBits(1);
            hdr.copyrightStart = brb.readBits(1);
            hdr.frameLength = brb.readBits(13);
            hdr.bufferFullness = brb.readBits(11);
            hdr.numAacFramesPerAdtsFrame = brb.readBits(2) + 1;
            if (hdr.numAacFramesPerAdtsFrame != 1) {
                throw new IOException("This muxer can only work with 1 AAC frame per ADTS frame");
            } else if (hdr.protectionAbsent != 0) {
                return hdr;
            } else {
                channel.read(ByteBuffer.allocate(2));
                return hdr;
            }
        } catch (EOFException e) {
            return null;
        }
    }

    private AdtsHeader readSamples(ReadableByteChannel channel) throws IOException {
        AdtsHeader first = null;
        while (true) {
            AdtsHeader hdr = readADTSHeader(channel);
            if (hdr == null) {
                return first;
            }
            if (first == null) {
                first = hdr;
            }
            ByteBuffer data;
            if (channel instanceof FileChannel) {
                data = ((FileChannel) channel).map(MapMode.READ_ONLY, ((FileChannel) channel).position(), (long) (hdr.frameLength - hdr.getSize()));
                this.samples.add(new SampleImpl(data));
                ((FileChannel) channel).position((((FileChannel) channel).position() + ((long) hdr.frameLength)) - ((long) hdr.getSize()));
                data.rewind();
            } else {
                data = ByteBuffer.allocate(hdr.frameLength - hdr.getSize());
                channel.read(data);
                this.samples.add(new SampleImpl(data));
                data.rewind();
            }
            this.stts.add(new Entry(1, 1024));
        }
    }

    public String toString() {
        return "AACTrackImpl{sampleRate=" + this.firstHeader.sampleRate + ", channelconfig=" + this.firstHeader.channelconfig + '}';
    }
}
