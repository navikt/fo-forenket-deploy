package no.nav.fo.forenkletdeploy.service

import no.nav.fo.forenkletdeploy.ApplicationConfig
import no.nav.fo.forenkletdeploy.consumer.TeamConfigConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class TeamService @Inject
constructor(
        val teamConfigConsumer: TeamConfigConsumer
) {
    val allTeams = arrayListOf(PTO(), TeamSoknad(), TeamVEDFP(), POA())

    fun getAppsForTeam(teamId: String): List<ApplicationConfig> =
            allTeams.find { it.id.equals(teamId, ignoreCase = true) }
                    ?.getApplicationConfigs(teamConfigConsumer) ?: emptyList()
}

abstract class Team constructor(
        val id: String,
        val displayName: String,
        val configUrl: String,
        val jenkinsFolder: String,
        val jenkinsUrl: String = "https://ci.adeo.no",
        val provideVersion: Boolean = false,
        val ignoredApplications: List<String> = emptyList(),
        val includeOnlyApplications: List<String> = emptyList(),
        var extraApps: List<ApplicationConfig> = emptyList(),
        val environments: List<String> = listOf("T6", "Q6", "Q0", "P"),
        val customizer: TeamCustomizer? = null
) {
    fun getApplicationConfigs(configConsumer: TeamConfigConsumer): List<ApplicationConfig> {

        var config = configConsumer.hentTeamConfig(uri = configUrl, useAuth = true)
                .entries
                .map { ApplicationConfig(name = it.key, gitUrl = it.value.gitUrl, team = this@Team) }
                .filter { !ignoredApplications.contains(it.name) }
                .union(extraApps)
                .toList()

        if (includeOnlyApplications.isNotEmpty()) {
            config = config.filter { includeOnlyApplications.contains(it.name) }
        }
        return config
    }
}

class PTO : Team(
        id = "pto",
        displayName = "Produktteam Oppfølging",
        configUrl = "https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/forenklet_oppfolging/config.json",
        jenkinsFolder = "forenklet_oppfolging",
        ignoredApplications = arrayListOf("badkitty", "veilarbdemo"),
        environments = listOf("Q0", "P")
) {
    init {
        extraApps = arrayListOf(
                ApplicationConfig(name = "veilarbportefoljeflatefs", gitUrl = "https://github.com/navikt/veilarbportefoljeflatefs", team = this),
                ApplicationConfig(name = "veilarbveileder", gitUrl = "https://github.com/navikt/veilarbveileder", team = this),
                ApplicationConfig(name = "veilarbportefolje", gitUrl = "https://github.com/navikt/veilarbportefolje", team = this),
                ApplicationConfig(name = "veilarbpersonflatefs", gitUrl = "https://github.com/navikt/veilarbpersonflatefs", team = this),
                ApplicationConfig(name = "aktivitetsplan", gitUrl = "https://github.com/navikt/aktivitetsplan", team = this),
                ApplicationConfig(name = "veilarbvisittkortfs", gitUrl = "https://github.com/navikt/veilarbvisittkortfs", team = this)
        )
    }
}

class TeamSoknad : Team(
        id = "sd",
        displayName = "Team søknad",
        configUrl = "https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/team_soknad/config.json",
        jenkinsFolder = "team_soknad",
        environments = listOf("T6", "Q0", "P")
) {
    init {
        extraApps = arrayListOf(
                ApplicationConfig(name = "soknad-kontantstotte", gitUrl = "https://github.com/navikt/soknad-kontantstotte.git", team = this),
                ApplicationConfig(name = "soknad-aap-utland", gitUrl = "https://github.com/navikt/soknad-aap-utland.git", team = this),
                ApplicationConfig(name = "soknad-kontantstotte-api", gitUrl = "https://github.com/navikt/soknad-kontantstotte-api.git", team = this),
                ApplicationConfig(name = "soknad-html-generator", gitUrl = "https://github.com/navikt/soknad-html-generator.git", team = this),
                ApplicationConfig(name = "soknad-kontantstotte-proxy", gitUrl = "https://github.com/navikt/soknad-kontantstotte-proxy.git", team = this)
        )
    }
}

class TeamVEDFP : Team(
        id = "teamforeldrepenger",
        displayName = "Team Foreldrepenger",
        configUrl = "https://raw.githubusercontent.com/navikt/team-vedtak/master/applikasjonsportefolje/config.json",
        jenkinsFolder = "teamforeldrepenger",
        environments = listOf("T10", "T4", "Q1", "Q0", "P")
)

class POA : Team(
        id = "POA",
        displayName = "POA",
        jenkinsFolder = "forenklet_oppfolging",
        environments = listOf("Q0", "P"),
        configUrl = "https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/forenklet_oppfolging/config.json",
        includeOnlyApplications = listOf(
                "jobbsokerkompetanse",
                "veilarbjobbsokerkompetanse",
                "veientilarbeid",
                "veiledearbeidssoker",
                "veiviserarbeidssoker",
                "arbeidssokerregistrering",
                "veilarbregistrering"
        )
)
