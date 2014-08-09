package com.gilt.lucene
import com.gilt.lucene._
import org.apache.lucene.document.Document
import com.gilt.lucene.LuceneFieldHelpers._
import org.apache.lucene.store.FSDirectory
import java.io.File
import com.gilt.lucene.LuceneText._
import org.apache.lucene.search.TermQuery
import org.apache.lucene.index.Term
import org.apache.lucene.document.TextField
import org.apache.lucene.document.Field.Store
import org.apache.lucene.document.Field.Index
import org.apache.lucene.document.Field
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.util.Version
import org.apache.lucene.store.RAMDirectory
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

class IndexAndSearchSuite extends FunSuite with ShouldMatchers {

  test("Index and search test using RAMDirectory with luceneText field") {
    val index = new ReadableLuceneIndex with LuceneStandardAnalyzer with WritableLuceneIndex with RamLuceneDirectory

    val textString ="Lucene sugar is cool"
    val doc1 = new Document
    doc1.addIndexedStoredField("field", textString.toLuceneText)
    index.addDocument(doc1)

    val queryParser = index.queryParserForDefaultField("field")
    val query = queryParser.parse("lucene")
    val results = index.searchTopDocuments(query, 1).toList
    results should have size 1
    results.head.get("field") should equal(textString)
    }

}