package kr.pieceofday.onelineserver.domain

import javax.persistence.*

@Entity
data class LikeLine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "line_id")
    var line: Line

)