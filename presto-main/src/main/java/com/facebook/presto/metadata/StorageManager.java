package com.facebook.presto.metadata;

import com.facebook.presto.block.BlockIterable;
import com.facebook.presto.operator.Operator;

import java.io.IOException;
import java.util.List;

public interface StorageManager
{
    void importShard(long shardId, List<? extends ColumnHandle> columnHandles, Operator source)
            throws IOException;

    BlockIterable getBlocks(long shardId, ColumnHandle columnHandle);

    boolean shardExists(long shardId);

    void dropShard(long shardId)
        throws IOException;

    ColumnFileHandle createStagingFileHandles(long shardId, List<? extends ColumnHandle> columnHandles)
        throws IOException;

    void commit(ColumnFileHandle columnFileHandle)
        throws IOException;
}