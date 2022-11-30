package kr.pieceofday.onelineserver.domain

import javax.persistence.*

@Entity
data class Line(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column
    var title: String,

    @Column
    var content: String,

    @Column
    var liked: Long,

    @Column
    var reported: Long,
): BaseEntity()
