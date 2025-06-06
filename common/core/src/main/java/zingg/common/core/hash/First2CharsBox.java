package zingg.common.core.hash;

public class First2CharsBox extends BaseHash<String,Integer>{
    
	public First2CharsBox() {
	    setName("first2CharsBox");
	}

	public Integer call(String field) {
		 if (field == null || field.length() <= 2) {
				return 0;
			} else {
				String sub = field.substring(0, 2);
				if (sub.compareTo("aa") >= 0 && sub.compareTo("jz") < 0) {
					return 1;
			} else if (sub.compareTo("jz") >= 0 && sub.compareTo("oz") < 0) {
					return 2;
			} else if (sub.compareTo("oz") >= 0) {
					return 3;
				} else {
					return 4;
				}
			}
	}

}
