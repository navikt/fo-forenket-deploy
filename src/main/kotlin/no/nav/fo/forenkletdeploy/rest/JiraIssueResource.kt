package no.nav.fo.forenkletdeploy.rest

import no.nav.fo.forenkletdeploy.service.JiraService
import org.springframework.web.bind.annotation.*

import javax.inject.Inject

@RestController
@RequestMapping("/api/jira")
class JiraIssueResource @Inject
constructor(val jiraService: JiraService) {
    @GetMapping("/{issueid}")
    fun getJiraIssue(@PathVariable("issueid") issueid: String) =
            jiraService.getUserStory(issueid).copy(key = issueid)
}
