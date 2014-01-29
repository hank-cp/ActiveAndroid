## Originally folk from [ActiveAndroid](https://github.com/hank-cp/ActiveAndroid)
#### This folk add multiple database support and some performance optimization

## Usage (different from original code stream)

#### Define a new DbMetaData class for db configuration

	public class UserDbMetaData extends DbMetaData {
	
		private String mUin = null;
	
		public UserDbMetaData(String uin){
			mUin = uin;
		}
	
		@Override
		public int getDatabaseVersion() {
			return 12;
		}
	
		@Override
		public String getDatabaseName() {
			return "user_"+mUin+".db";
		}
	
		@Override
		public String getMigrationPath() {
			return "migration/user";
		}
	
	    /**
	     * When this flag return true, opening database under this dbMetaData will be 
	     * closed if new one is register. As example:
	     *
	     * registerDb(1) -- database user_1.db is opened
	     * registerDb(2) -- database user_1.db is closed and user_2.db is opened
	     */
		@Override
		public boolean isResettable() {
			return true; 
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion < 5) {
				// do somthing
			}
			if (newVersion >= 7) {
				// do somthing
			}
		}
	
	    /**
	     * You need to override equals method to tell ActiveAndroid how to 
	     * identify DbMetaData to avoid open-close database too often
	     */
		@Override
		public boolean equals(Object anotherObj) {
			UserDbMetaData anotherUserDbMetaData = (UserDbMetaData) anotherObj;
			return (mUin.equals(anotherUserDbMetaData.mUin));
		}
	
	    /**
	     * Call me when you are ready to open a database
	     */
		public static void registerDb(String uin) {
			UserDbMetaData dbMetaData = new UserDbMetaData(uin);
			ActiveAndroid.registerDbMetaData(Configuration.getInstance().getAppContext(), dbMetaData);
		}
	
	}

#### Annotate @DatabaseMetaData to your modal class

	@DatabaseMetaData(metadataClass = UserDbMetaData.class)
	public class Album extends Model implements Serializable { ... }

#### Ehancement

* If you add new modal class, just increate the value return by `getDatabaseVersion()`, ActiveAndroid
will be able to setup missing table on next registration.
* Alternative to using @DatabaseMetaData, you could also tell ActiveAndroid where is all your modal class via 
`res/xml/db_model_config.xml`. Rather than looking up all annotated class in dex.jar, 
this approach will significantly improve your App's startup time.

#### Reset are almost the same. Enjoy it~ 