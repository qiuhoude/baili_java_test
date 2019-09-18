package com.houde.test;

public class Turple<T, V> {
	private T a;
	private V b;

	public Turple(T a, V b) {
		this.setA(a);
		this.setB(b);
	}

	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public V getB() {
		return b;
	}

	public void setB(V b) {
		this.b = b;
	}

    @Override
    public String toString() {
        return "Turple [a=" + a + ", b=" + b + "]";
    }

	public static void main(String[] args) {
		GetLordBaseType type = GetLordBaseType.getByName("equip");
		System.out.println(type.getId()+"_"+type.getName());
	}
}


/**
 * 获取玩家信息的类型
 *
 */
  enum GetLordBaseType {
	PROP(1, "prop"), // 背包道具
	EQUIP(2, "equip"), // 装备
	HERO(3, "hero"), // 将领
	BUILD(4, "build"), // 基础建筑和资源建筑
	SUPEREQUIP(5, "superEquip"), // 超级武器
	TECH(6, "tech"), // 科技
	EXT(7, "ext"),// 扩展字段
	;

	public static GetLordBaseType getByName(String name) {
		if(name==null){
			return null;
		}
		for (GetLordBaseType type : values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}
	public static GetLordBaseType getById(int id) {
		for (GetLordBaseType type : values()) {
			if (type.getId() == id) {
				return type;
			}
		}
		return null;
	}

	private int id;
	private String name;

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	private GetLordBaseType(int id, String name) {
		this.id = id;
		this.name = name;
	}
}