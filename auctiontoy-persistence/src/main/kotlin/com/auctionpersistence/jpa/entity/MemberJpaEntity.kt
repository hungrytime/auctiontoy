package com.auctionpersistence.jpa.entity

import com.auctionpersistence.jpa.entity.base.BaseEntity
import com.auctiontoydomain.Member
import com.auctiontoydomain.MemberStatus
import javax.persistence.*

@Entity
@Table(name = "members")
class MemberJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null,

    val userId: String,
    val password: String,
    val name: String,

    @Enumerated(EnumType.STRING)
    val memberStatus: MemberStatus = MemberStatus.ACTIVATED

) : BaseEntity() {
    companion object {
        fun from(member: Member) = MemberJpaEntity(
                memberId = member.memberId,
                memberStatus = member.memberStatus,
                userId = member.id,
                password = member.password,
                name = member.name
            )
    }

    fun to() = Member(
        memberId = this.memberId,
        memberStatus = this.memberStatus,
        id = this.userId,
        password = this.password,
        name = this.name,
        createdDate = this.createdAt.toString()
    )
}