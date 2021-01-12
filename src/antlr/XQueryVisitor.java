// Generated from XQuery.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XQueryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XQueryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link XQueryParser#r}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR(XQueryParser.RContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#fc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFc(XQueryParser.FcContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#wc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWc(XQueryParser.WcContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#lc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLc(XQueryParser.LcContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#rc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRc(XQueryParser.RcContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin(XQueryParser.JoinContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(XQueryParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitC(XQueryParser.CContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAp(XQueryParser.ApContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRp(XQueryParser.RpContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF(XQueryParser.FContext ctx);
}