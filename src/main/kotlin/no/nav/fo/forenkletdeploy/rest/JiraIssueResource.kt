package no.nav.fo.forenkletdeploy.rest

import no.nav.fo.forenkletdeploy.service.JiraService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

import javax.inject.Inject

@RestController
@RequestMapping("/api/jira")
class JiraIssueResource @Inject
constructor(val jiraService: JiraService) {
    val LOG = LoggerFactory.getLogger(JiraIssueResource::class.java)

    @GetMapping
    fun getJiraIssues(@RequestParam(name = "issue") issues: List<String>) =
            issues
                    .filter { !it.startsWith("kaizen", ignoreCase = true) }
                    .mapNotNull {
                        try {
                            jiraService.getUserStory(issueId = it)
                        } catch (ex: Exception) {
                            LOG.error("Kunne ikke finne jira-issue $it", ex)
                            null
                        }
                    }

    @GetMapping("/{issueid}")
    fun getJiraIssue(@PathVariable("issueid") issueid: String) =
            jiraService.getUserStory(issueid).copy(key = issueid)
}
