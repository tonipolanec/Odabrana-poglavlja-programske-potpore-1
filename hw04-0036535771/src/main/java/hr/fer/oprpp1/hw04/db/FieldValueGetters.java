package hr.fer.oprpp1.hw04.db;

/**
 * Class for all String field value getters: <br>
 * <code>FIRST_NAME</code>, <code>LAST_NAME</code>, <code>JMBAG</code>
 * 
 * @author Toni Polanec
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();

	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();

	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();

}
