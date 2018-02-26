package model;

import java.util.List;

public class NestedBranchPrice {

	private final List<BranchLocation> branches;

	private final float aggregatedPrice;

	public NestedBranchPrice(List<BranchLocation> branches, float aggregatedPrice) {
		this.branches = branches;
		this.aggregatedPrice = aggregatedPrice;
	}

	public List<BranchLocation> getBranches() {
		return branches;
	}

	public float getAggregatedPrice() {
		return aggregatedPrice;
	}

}
