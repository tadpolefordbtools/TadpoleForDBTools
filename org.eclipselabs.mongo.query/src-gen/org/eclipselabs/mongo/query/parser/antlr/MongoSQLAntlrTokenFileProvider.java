/*
* generated by Xtext
*/
package org.eclipselabs.mongo.query.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class MongoSQLAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("org/eclipselabs/mongo/query/parser/antlr/internal/InternalMongoSQL.tokens");
	}
}
