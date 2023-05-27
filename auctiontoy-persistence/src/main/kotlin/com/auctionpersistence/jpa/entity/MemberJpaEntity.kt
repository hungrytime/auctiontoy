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

    val id: String,
    val password: String,
    val name: String,

    @Enumerated(EnumType.STRING)
    val memberStatus: MemberStatus = MemberStatus.ACTIVATED,

    @OneToMany
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    val items: List<ItemJpaEntity> = listOf()

) : BaseEntity() {
    companion object {
        fun from(member: Member) = MemberJpaEntity(
                memberId = member.memberId,
                memberStatus = member.memberStatus,
                id = member.id,
                password = member.password,
                name = member.name
            )
    }

    fun to() = Member(
        memberId = this.memberId,
        memberStatus = this.memberStatus,
        id = this.id,
        password = this.password,
        name = this.name,
        createdDate = this.createdAt.toString()
    )
}