package kr.pieceofday.onelineserver.domain

import javax.persistence.*

@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column
    var name: String,

): BaseEntity()
