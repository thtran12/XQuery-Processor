# XQuery-Processor

Query processor for a subset of XML's data model.
After receiving an XQuery, the processor parses it into an abstract tree representation using ANTLR4, optimizes it (detects and minimizes the number of joins), and executes the optimized plan.

