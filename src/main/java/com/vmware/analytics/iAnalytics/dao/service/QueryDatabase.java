package com.vmware.analytics.iAnalytics.dao.service;

public class QueryDatabase {
    /*
    public static void main(String[] args) {
        FDB fdb = FDB.selectAPIVersion(300);
        final Database db = fdb.open();

            // Run an operation on the database
            db.run(tr -> {
                tr.set(Tuple.from("hello").pack(), Tuple.from("world").pack());
                return null;
            });

            // Get the value of 'hello' from the database
            String hello = db.run(tr -> {
                byte[] result = tr.get(Tuple.from("hello").pack()).get();
                return Tuple.fromBytes(result).getString(0);
            });
            System.out.println("Hello " + hello);

        int rows = db.run(tr -> {
            //Range r = new Range(Tuple.from("h").pack(), Tuple.from("i").pack());
            Range r = new Range(new byte[]{2, 'h', 0}, Tuple.from("i").pack());
            AsyncIterator<KeyValue> itr = tr.getRange(r).iterator();
            int i=0;
            while(itr.hasNext()) {
                i++;
                KeyValue kv = itr.next();
                System.out.println("Key: "+ String.valueOf(kv.getKey())+" Value: "+kv.getValue().length);
            }
            //return Tuple.fromBytes(result).getString(0);
            return i;
        });
        System.out.println("Found rows "+ rows);

        db.dispose();

        byte[] test = Tuple.from("h").pack();
        System.out.println(new String(test));
        System.out.println(String.format("0x%02X ", test[0]));
        System.out.println(String.format("0x%02X ", test[1]));
        System.out.println(test[2]);
    }
*/
    private QueryDatabase() {}
}
