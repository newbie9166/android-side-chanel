package com.android.proc;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;


public class MemInfo extends AbstractProcInfo {
    private static final String[] wantedFields = {"MemFree", "MemTotal", "MemAvailable", "Buffers", "Cached", "SwapCached",
            "Active", "Inactive", "ActiveAnon", "InactiveAnon", "ActiveFile", "InactiveFile", "Unevictable",
            "Mlocked", "SwapTotal", "SwapFree", "Dirty", "Writeback", "AnonPages", "Mapped", "Shmem", "Slab",
            "SReclaimable", "SUnreclaim", "KernelStack", "PageTables", "NFS_Unstable", "Bounce", "WritebackTmp",
            "CommitLimit", "Committed_AS", "VmallocTotal", "VmallocUsed", "VmallocChunk", "HardwareCorrupted",
            "AnonHugePages", "HugePages_Total", "HugePages_Free", "HugePages_Rsvd", "HugePages_Surp",
            "Hugepagesize", "DirectMap4k", "DirectMap2M", "DirectMap1G"};
    public static final String filePath = "/proc/meminfo";

    public MemInfo() {
        update();
    }

    @Override
    protected String[] getWantedFields() {
        return wantedFields;
    }

    @Override
    protected String getFilePath() {
        return filePath;
    }
}