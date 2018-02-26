package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractCustomerInfoQuery extends AbstractQuery<Map<String, String>> {

	private final String[] selected;

	public AbstractCustomerInfoQuery(String[] selected) {
		this.selected = selected;
	}

	@Override
	protected Map<String, String> parseResult(ResultSet rs) throws SQLException {
		Map<String, String> props = new HashMap<>();
		if (rs.next()) {
			for (String attribute : selected) {
				props.put(attribute, rs.getString(attribute));
			}
			return props;
		}
		throw new SQLException("No customer found with specified identification");
	}

	@Override
	protected String getQueryDefinition() {
		return getQueryDefinition(formatAttributes(selected));
	}

	protected abstract String getQueryDefinition(String formattedAttributes);

	private String formatAttributes(String[] attributes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < attributes.length; i++) {
			s.append(attributes[i]);
			if (i != attributes.length - 1) {
				s.append(", ");
			}
		}
		return s.toString();
	}

}
