package chenjie.stock.common;

import chenjie.stock.common.infrastructure.hbase.HBaseStore;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FakeMainApplication {

    @Autowired
    private HBaseStore store;

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        String tableName = "test_statements";
        TableName name = TableName.valueOf(tableName);
        Admin admin = connection.getAdmin();
        ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.of("t");
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(name)
                .setColumnFamily(columnFamilyDescriptor)
                .build();
        admin.createTable(tableDescriptor);
        admin.close();

        Table table = connection.getTable(name);

    }
}
