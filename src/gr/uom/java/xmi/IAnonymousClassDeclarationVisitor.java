package gr.uom.java.xmi;

import java.util.Set;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;

public interface IAnonymousClassDeclarationVisitor {

	boolean visit(AnonymousClassDeclaration node);

	Set<AnonymousClassDeclaration> getAnonymousClassDeclarations();

}