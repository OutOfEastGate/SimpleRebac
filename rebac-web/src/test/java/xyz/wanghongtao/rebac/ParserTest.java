package xyz.wanghongtao.rebac;

import org.junit.Test;

/**
 * @author wanghongtao
 * @data 2023/7/18 22:52
 */
public class ParserTest {
    @Test
    public void parseTupleTest() {
        String triplet = "document:doc1#reader@user:user1";

        String[] parts = triplet.split("#|@|:");

        String objectType = parts[0];
        String object = parts[1];
        String relation = parts[2];
        String subject = parts[3];
        String subjectType = parts[4];

        System.out.println("objectType: " + objectType);
        System.out.println("object: " + object);
        System.out.println("relation: " + relation);
        System.out.println("subjectType: " + subjectType);
        System.out.println("subject: " + subject);
    }
}
