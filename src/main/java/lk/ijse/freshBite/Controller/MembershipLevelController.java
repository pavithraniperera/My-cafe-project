package lk.ijse.freshBite.Controller;

import lk.ijse.freshBite.dto.MembershipLevelDto;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MembershipLevelController {
    @Getter
    private static final Map<String, MembershipLevelDto> membershipLevels = new HashMap<>();

    static {
        // Initialize membership levels with default discount percentages
        membershipLevels.put("VIP", new MembershipLevelDto("VIP", 15));
        membershipLevels.put("Silver", new MembershipLevelDto("Silver", 10));
        membershipLevels.put("Gold", new MembershipLevelDto("Gold", 20));
        membershipLevels.put("Bronze", new MembershipLevelDto("Bronze", 5));
        membershipLevels.put("None", new MembershipLevelDto("None", 0));
    }

    public static void setDiscountPercentage(String membership, double discountPercentage) {
        MembershipLevelDto level = membershipLevels.get(membership);
        if (level != null) {
            level.setDiscountPercentage(discountPercentage);
        }
    }
}
