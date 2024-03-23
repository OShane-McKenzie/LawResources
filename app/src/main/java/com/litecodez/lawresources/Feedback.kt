package com.litecodez.lawresources

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.docs.v1.Docs
import com.google.api.services.docs.v1.DocsScopes
import com.google.api.services.docs.v1.model.BatchUpdateDocumentRequest
import com.google.api.services.docs.v1.model.DeleteContentRangeRequest
import com.google.api.services.docs.v1.model.InsertTextRequest
import com.google.api.services.docs.v1.model.Location
import com.google.api.services.docs.v1.model.Range
import com.google.api.services.docs.v1.model.Request
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.Collections

class Feedback() {
    private val credentialsJson: String = contentProvider.feedbackCredential.value
    private val applicationName: String = appName
    private val SCOPES = Collections.singletonList(DocsScopes.DOCUMENTS)
    private val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val JSON_FACTORY = GsonFactory.getDefaultInstance()
    private val docs: Docs = initializeDocsService()
    private val docId = feedBackID

    private fun initializeDocsService(): Docs {
        val credentials = GoogleCredential.fromStream(
            ByteArrayInputStream(credentialsJson.toByteArray(Charsets.UTF_8))
        ).createScoped(SCOPES)

        return Docs.Builder(httpTransport, JSON_FACTORY, credentials)
            .setApplicationName(applicationName)
            .build()
    }

    fun readDocument(documentId: String = docId, callBack:()->Unit={}): String {
        return try {
            val response = docs.documents().get(documentId).execute()
            val content = response.body.content?.joinToString("") { paragraph ->
                paragraph.paragraph?.elements?.joinToString("") { element ->
                    element.textRun?.content ?: ""
                } ?: ""
            } ?: ""
            content.trim()
        } catch (e: GoogleJsonResponseException) {
            println("Error reading document: ${e.message}")
            ""
        } catch (e: IOException) {
            println("Error reading document: ${e.message}")
            ""
        }finally {
            callBack()
        }
    }

    fun writeDocument(documentId: String = docId, content: String, callBack: (String) -> Unit={}): Boolean {

        return try {
            val requests = mutableListOf<Request>()
            val deleteContentRequest = Request().setDeleteContentRange(
                DeleteContentRangeRequest()
                    .setRange(Range().setStartIndex(1).setEndIndex(readDocument().length+1)) // Delete all content in the document
            )
            requests.add(deleteContentRequest)

            // Insert new content at index 1 (or any other index you prefer)
            val insertContentRequest = Request().setInsertText(
                InsertTextRequest()
                    .setText(content)
                    .setLocation(Location().setIndex(1)) // Index 1 indicates the start of the document
            )
            requests.add(insertContentRequest)

            // Execute the batch update request with both delete and insert requests
            docs.documents().batchUpdate(documentId, BatchUpdateDocumentRequest().setRequests(requests)).execute()
            callBack("")
            true
        } catch (e: GoogleJsonResponseException) {
            callBack("Error writing document: ${e.message}")
            false
        } catch (e: IOException) {
            callBack("Error writing document: ${e.message}")
            false
        }
    }
}