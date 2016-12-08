package apache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.MapTransformer;
import org.apache.commons.collections.functors.TruePredicate;
import org.testng.annotations.Test;
import org.zhangkang.entity.User;

import java.util.*;

/**
 * Created by Administrator on 12/8/2016.
 */
public class CollectionUtilsTest {

    List<String> list1 = Lists.newArrayList("a", "b", "c", "b", "f");
    List<String> list2 = new ArrayList<String>();
    Set<String> set1 = Sets.newHashSet("a", "b", "f");


    @Test
    public void testIsEmpty() {

        System.out.println(CollectionUtils.isEmpty(list1));
        System.out.println(CollectionUtils.isEmpty(list2));
        System.out.println(CollectionUtils.isEmpty(null));
    }

    @Test
    public void testFind() {
        // 找到符合断言的元素
        Object str = CollectionUtils.find(list1, new MyPredicate());
        System.out.println(str);
    }

    @Test
    public void testSelect() {
        // 不影响原来集合
        Collection collection = CollectionUtils.select(list1, new MyPredicate());
    }

    @Test
    public void testFilter() {
        // 直接重原来集合过滤出
        CollectionUtils.filter(list1, new MyPredicate());
    }

    @Test
    public void testTransform() {
        CollectionUtils.transform(list1, new MyTransform());
        CollectionUtils.transform(set1, new MyTransform());
    }


    @Test
    public void testCollect() {
        // 转换为另一种集合
        Collection list2 = CollectionUtils.collect(list1, new MyTransform());
        Collection set2 = CollectionUtils.collect(set1, new MyTransform());
    }

    @Test
    public void testTransformMap() {
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "aa");
        map.put("b", "bb");
        map.put("c", "cc");
        CollectionUtils.transform(list1, MapTransformer.getInstance(map));
        CollectionUtils.transform(set1, MapTransformer.getInstance(map));
    }

    @Test
    public void testForAllDo() {
        List<User> userList = Lists.newArrayList();

        final User user1 = new User();
        user1.setAge(10);
        User user2 = new User();
        user2.setAge(20);
        userList.add(user1);
        userList.add(user2);

        CollectionUtils.forAllDo(userList, new Closure() {
            public void execute(Object input) {
                if(input instanceof User) {
                    User user = (User)input;
                    user.setAge(user.getAge() + 1);
                }
            }
        });
    }

    @Test
    public void testAll(){
        CollectionUtils.addAll(list1, new String[]{"H"});
    }

    @Test
    public void testIsEqualCollection() {
        List<String> list1 = Lists.newArrayList("a", "b", "b");
        List<String> list2 = Lists.newArrayList("b", "b", "a");
        System.out.println(CollectionUtils.isEqualCollection(list1, list2));
    }



    class MyPredicate implements Predicate {

        public boolean evaluate(Object object) {
            if(object!=null && object.equals("b")) {
                return true;
            }
            return false;
        }
    }

    class MyTransform implements Transformer {

        public Object transform(Object input) {
            if("a".equals(input)) {
                return "A";
            }
            return input+"_";
        }
    }
}
