package com.necromantus.parser;

import com.necromantus.ParserException;
import com.necromantus.token.Token;
import com.necromantus.token.TokenIdManager;
import com.necromantus.tokenizer.Tokenizer;

import java.util.ArrayList;

/**
 * 21:50 now. DO NOT USE FUNCTIONS! they don't work. Now want to make ifs and loops should be easier, haha.
 * <p>
 * TODO:
 * parse if. ({@code Walker} if! (no scopes for now.))
 * LOOPS!
 */
public final class Parser {

    public static Tokenizer tokenizer;

    public ArrayList<String> reserved = new ArrayList<>();

    public Parser(Tokenizer t) {
        tokenizer = t;
        reserved.add("let");
        reserved.add("func");
        reserved.add("if");
        reserved.add("loop ");
    }






    //<--------------------- HELPERS --------------------->//

    private Node parseAtom() throws ParserException {
        Token tok = tokenizer.nextToken();
        if (tok.id == 0 && !reserved.contains(tok.value)) {
            return new Node(NodeType.VALUE, NodeType.ValueNodeType.VAR, tok.value);
        } else if (tok.id == 1) {
            return new Node(NodeType.VALUE, NodeType.ValueNodeType.NUMBER, tok.value);
        } else if (tok.id == TokenIdManager.getIdByDesc("STRING")) {
            return new Node(NodeType.VALUE, NodeType.ValueNodeType.STRING, tok.value.substring(1, tok.value.length() - 1));
        } else
            throw new ParserException("Expected number, string or name");
    }

    private Node parseVar() throws ParserException {
        Token tok = tokenizer.nextToken();
        if (tok.id == 0 && !reserved.contains(tok.value))
            return new Node(NodeType.VALUE, NodeType.ValueNodeType.VAR, tok.value);
        else
            throw new ParserException("Expected name");
    }


    private Node parseExpressionOrAtom() throws ParserException {
        Node n;
        Token openingThingOrLhs = tokenizer.seekToken();
        if (openingThingOrLhs.id == TokenIdManager.getIdByDesc("(")) {
            tokenizer.nextToken();
            n = parseExpr();
            Token token = tokenizer.nextToken();
            if (token.id == TokenIdManager.getIdByDesc(")")) {
                return n;
            } else {
                throw new ParserException(") expected");
            }
        } else {
            n = parseAtom();
        }
        return n;
    }




    //<--------------------- FLOW CONTROL --------------------->//

    private IfNode parseIf() throws ParserException {
        Node expression = parseExpr();
        BlockNode block = parseBlock();
        Token elseIfQM = tokenizer.seekToken();
        IfNode elseIf = null;
        Node elseNode = null;
        if(elseIfQM.id == 0 && elseIfQM.value.equals("elseif")) {
            tokenizer.nextToken();
            elseIf = parseIf();
        }
        if(elseIfQM.id == 0 && elseIfQM.value.equals("else")) {
            tokenizer.nextToken();
            elseNode = parseBlock();
        }
        return new IfNode(expression, block, elseIf, elseNode);
    }

    private Node parseLoop() throws ParserException {
        Node expression = parseExpr();
        BlockNode block = parseBlock();
        return new Node(NodeType.LOOP, null, "LOOP", expression, block);
    }





    //<--------------------- FUNCTIONS --------------------->//

    /**
     * Parses arguments of a function call.
     * @return Argument List Node.
     * @throws ParserException When incorrect syntax.
     */
    private Node parseFuncArgs() throws ParserException {
        ListNode n = new ListNode(NodeType.ARG_LIST, null, "ARG_LIST");
        Token ob = tokenizer.nextToken();
        if (ob.id == TokenIdManager.getIdByDesc("(")) {
            Token v = tokenizer.nextToken();
            if (v.id == 0) {
                n.add(new ListNode(NodeType.VALUE, NodeType.ValueNodeType.VAR, v.value));
                Token comma = tokenizer.seekToken();
                while (comma.id == TokenIdManager.getIdByDesc(",")) {
                    tokenizer.nextToken();
                    n.add(parseVar());
                    comma = tokenizer.seekToken();
                }
                if (tokenizer.nextToken().id != TokenIdManager.getIdByDesc(")")) {
                    throw new ParserException("Expected ')'");
                }
            } else if (v.id == TokenIdManager.getIdByDesc(")")) {
                tokenizer.nextToken(); // ) only after (
            } else {
                throw new ParserException("Expected ')'");
            }
        } else throw new ParserException("Expected '('");
        return n;
    }



    /**
     * Parse call of a function. (does not parse call keyword)
     * @return Function Call Node.
     * @throws ParserException when there is incorrect syntax.
     */
    private Node parseFuncCall() throws ParserException {
        Node callName = parseVar();
        return parseFuncCallWithName((String)callName.value);
    }
    /**
     * Parse call of a function. (does not parse call keyword)
     * @return Function Call Node.
     * @throws ParserException when there is incorrect syntax.
     */
    private Node parseFuncCallWithName(String name) throws ParserException {
        Node n = new Node(NodeType.ACTION, NodeType.ActionNodeType.FUNC_CALL, "FUNC_CALL");
        Node callArgs = parseFuncCallArgs();
        n.add(false, new Node(NodeType.VALUE, NodeType.ValueNodeType.VAR, name));
        n.add(true, callArgs);
        return n;
    }



    /**
     * Parses function call arguments.
     * @return Arguments node.
     * @throws ParserException when there is incorrect syntax.
     */
    private Node parseFuncCallArgs() throws ParserException {
        ListNode n = new ListNode(NodeType.ARG_CALL_LIST, null, "ARG_CALL_LIST");
        Token ob = tokenizer.nextToken();
        if (ob.id == TokenIdManager.getIdByDesc("(")) {
            Token v = tokenizer.seekToken();

            if (v.id == TokenIdManager.getIdByDesc(")")) {
                tokenizer.nextToken();
            } else {
                ListNode ln = new ListNode(NodeType.VALUE, NodeType.ValueNodeType.FUNC_CALL_EXPR, "FUNC_CALL_EXPR");
                ln.add(false, parseExpr());
                n.add(ln);
                Token comma = tokenizer.seekToken();
                while (comma.id == TokenIdManager.getIdByDesc(",")) {
                    tokenizer.nextToken();
                    n.add(parseExpr());
                    comma = tokenizer.seekToken();
                }

                Token v2 = tokenizer.nextToken();
                if (v2.id != TokenIdManager.getIdByDesc(")"))
                    throw new ParserException("Expected ')'");
            }
        }
        return n;
    }



    /**
     * Parses function. (func keyword is not parsed here, it is parsed in {@code parseExpr}
     * @return Function Node
     * @throws ParserException when there is incorrect syntax.
     */
    private Node parseFunc() throws ParserException {
        Node n = new Node(NodeType.FUNC, null, "FUNC");
        Node args = parseFuncArgs();
        n.add(false, args);
        n.add(true, parseBlock());
        return n;
    }





    //<--------------------- OPERATORS --------------------->//

    private Node parseLogical() throws ParserException {
        Node n;
        n = parseExpressionOrAtom();
        Token operatorToken = tokenizer.seekToken();
        while (operatorToken.id == TokenIdManager.getIdByDesc("|") || operatorToken.id == TokenIdManager.getIdByDesc("&")) {
            tokenizer.nextToken(); // skip * or /
            n = new Node(NodeType.ACTION, NodeType.ActionNodeType.OPERATOR, operatorToken.value, n, parseExpressionOrAtom());
            operatorToken = tokenizer.seekToken();
        }
        return n;
    }

    private Node parseEquality() throws ParserException {
        Node n;
        n = parseLogical();
        Token operatorToken = tokenizer.seekToken();
        while (operatorToken.id == 0 && operatorToken.value.equals("is") || operatorToken.id == 0 && operatorToken.value.equals("not")) {
            tokenizer.nextToken(); // skip * or /
            n = new Node(NodeType.ACTION, NodeType.ActionNodeType.OPERATOR, operatorToken.value, n, parseLogical());
            operatorToken = tokenizer.seekToken();
        }
        return n;
    }

    private Node parseComparison() throws ParserException {
        Node n;
        n = parseEquality();
        Token operatorToken = tokenizer.seekToken();
        while (operatorToken.id == TokenIdManager.getIdByDesc(">") || operatorToken.id == TokenIdManager.getIdByDesc("<")) {
            tokenizer.nextToken(); // skip * or /
            n = new Node(NodeType.ACTION, NodeType.ActionNodeType.OPERATOR, operatorToken.value, n, parseEquality());
            operatorToken = tokenizer.seekToken();
        }
        return n;
    }

    private Node parseMul() throws ParserException {
        Node n;
        n = parseComparison();
        Token operatorToken = tokenizer.seekToken();
        while (operatorToken.id == TokenIdManager.getIdByDesc("*") || operatorToken.id == TokenIdManager.getIdByDesc("/")) {
            tokenizer.nextToken(); // skip * or /
            n = new Node(NodeType.ACTION, NodeType.ActionNodeType.OPERATOR, operatorToken.value, n, parseComparison());
            operatorToken = tokenizer.seekToken();
        }
        return n;
    }

    private Node parseAdd() throws ParserException {
        Node n = parseMul();
        Token addTok = tokenizer.seekToken();
        while (addTok.id == TokenIdManager.getIdByDesc("+") || addTok.id == TokenIdManager.getIdByDesc("-")) {
            tokenizer.nextToken();
            n = new Node(NodeType.ACTION, NodeType.ActionNodeType.OPERATOR, addTok.value, n, parseMul());
            addTok = tokenizer.seekToken();
        }
        return n;
    }



    //<--------------------- STATEMENTS --------------------->//

    // btw i know i can put tokenizer to class field. ah never mind ill do thatokenizer.


    public Node parseStatement() throws ParserException {
        NodeType.StatementNodeType thing; // so no duplicates.
        Token letQM = tokenizer.seekToken();
        if (letQM.value.equals("let")) {
            thing = NodeType.StatementNodeType.VAR_DEFINITION;
            tokenizer.nextToken();
        } else if(letQM.value.equals("if")) {
            tokenizer.nextToken();
            //if(tokenizer.nextToken().id != TokenIdManager.getIdByDesc(";"))
            //    throw new ParserException("Expected ';'");
            return parseIf();
        } else if(letQM.value.equals("while")) {
            tokenizer.nextToken();
            //            if(tokenizer.nextToken().id != TokenIdManager.getIdByDesc(";"))
//                throw new ParserException("Expected ';'");
            return parseLoop();
        } else {
            thing = NodeType.StatementNodeType.VAR_ASSIGNMENT;
        }
        Node name = parseVar();
        Token eqOperator = tokenizer.seekToken();
        if (eqOperator.id == TokenIdManager.getIdByDesc("=")) {
            tokenizer.nextToken();
            Node val = parseExpr();
            Token semicolon = tokenizer.nextToken();
            if (semicolon.id == TokenIdManager.getIdByDesc(";")) {
                return new Node(NodeType.STATEMENT, thing, "STATEMENT", name, val);
            } else {
                throw new ParserException("Expected ';'");
            }
        } else if (eqOperator.id == TokenIdManager.getIdByDesc("(")) {
            Node n = parseFuncCallWithName((String)name.value);
            Token semicolon = tokenizer.nextToken();
            if(semicolon.id == TokenIdManager.getIdByDesc(";"))
                return n;
            else
                throw new ParserException("Expected ';'");
        }
        throw new ParserException("Expected '=' or '('");
    }

//    public Node parseIf() throws Exception {
//        Node n = new Node(NodeType.IF, null, "IF");
//
//    }

    private BlockNode parseBlock() throws ParserException {
        BlockNode n = new BlockNode(NodeType.BLOCK, null, "BLOCK");
        Token opening = tokenizer.nextToken();
        if (opening.id == TokenIdManager.getIdByDesc("{")) {
            while (tokenizer.seekToken().id != TokenIdManager.getIdByDesc("}")) {
                n.add(parseStatement());
            }
            Token closing = tokenizer.nextToken();
            if (closing.id != TokenIdManager.getIdByDesc("}")) {
                throw new ParserException("Expected }");
            }
        }
        return n;
    }



    private Node parseExpr() throws ParserException {
        Token kwQM = tokenizer.seekToken();
        if (kwQM.id == 0 && kwQM.value.equals("func")) {
            tokenizer.nextToken();
            return parseFunc();
        } else if (kwQM.id == 0 && kwQM.value.equals("if")) {
            tokenizer.nextToken();
            return parseIf();
        } else if (kwQM.id == 0 && kwQM.value.equals("while")) {
            tokenizer.nextToken();
            return parseLoop();
        } else {
            if (kwQM.id != TokenIdManager.getIdByDesc("STRING")) {
                Token ob = tokenizer.seekToken(1);
                if(ob.id == TokenIdManager.getIdByDesc("(")) {
                    // old, hehe. tokenizer.nextToken(); // skip the name. idk why, could've just used parseFuncCall(), but who cares?
                    return parseFuncCall();
                } else {
                    Node expr = parseAdd();
                    return new Node(NodeType.EXPRESSION, null, "EXPRESSION", expr, null);
                }
            } else {
                tokenizer.nextToken();
                return new Node(NodeType.EXPRESSION, null, "EXPRESSION", new Node(NodeType.VALUE, NodeType.ValueNodeType.STRING, kwQM.value.substring(1, kwQM.value.length() - 1)), null);
            }
        }
    }

    private void log(String ...vals) {
        for(String val : vals) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public Node parseSource() throws ParserException {
        RootNode n = new RootNode(NodeType.ROOT, null, "SOURCE");
        while (tokenizer.canWeLookAtNextTokenQM()) {
            n.add(parseStatement());
        }
        return n;
    }
}

// 345