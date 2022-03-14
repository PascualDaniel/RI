package uo.ri.cws.domain;

import java.util.Objects;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Recommendation extends BaseEntity {

	public boolean usedForVoucher;

	private Client sponsor;

	private Client recommended;

	public Recommendation() {
	}

	public Recommendation(Client sponsor, Client recommended) {
		ArgumentChecks.isNotNull(sponsor);
		ArgumentChecks.isNotNull(recommended);
		Associations.Recommend.link(sponsor, recommended, this);
	}

	public boolean isUsed() {
		return usedForVoucher;
	}

	public void setUsed(boolean used) {
		this.usedForVoucher = used;
	}

	public Client getSponsor() {
		return sponsor;
	}

	public void _setSponsor(Client sponsor) {
		this.sponsor = sponsor;
	}

	public Client getRecommended() {
		return recommended;
	}

	public void _setRecommended(Client recommended) {
		this.recommended = recommended;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(recommended, sponsor);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recommendation other = (Recommendation) obj;
		return Objects.equals(recommended, other.recommended)
				&& Objects.equals(sponsor, other.sponsor);
	}

	@Override
	public String toString() {
		return "Recommendation [used=" + usedForVoucher + "]";
	}
}