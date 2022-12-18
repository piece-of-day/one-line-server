package kr.pieceofday.onelineserver.domain

import kr.pieceofday.onelineserver.dto.ResponseLineDTO
import javax.persistence.*

@Entity
data class Line(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var title: String = "",

    @Column
    var content: String = "",

    @Column
    var liked: Long = 0,

    @Column
    var reported: Long = 0,

    @ManyToOne
    var user: User
): BaseEntity()
