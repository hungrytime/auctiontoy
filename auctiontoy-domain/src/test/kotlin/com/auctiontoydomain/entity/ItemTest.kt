package com.auctiontoydomain.entity

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.LocalDateTime

internal class ItemTest: BehaviorSpec() {
    // Spec 클래스의 하나의 인스턴스가 생성된 다음 모든 테스트가 완료될 때까지 각 테스트 케이스가 차례로 실행되는 방식
    override fun isolationMode(): IsolationMode = IsolationMode.SingleInstance

    init {
        val defaultTestItemInfo = Item (
            itemId = 1,
            memberId = 1,
            name = "PreItem1",
            itemStatus = ItemStatus.PREPARE_AUCTION,
            basePrice = BigDecimal(1000),
            realTimePrice = BigDecimal.ZERO,
            desiredPrice = BigDecimal(6000),
            minimumPrice = BigDecimal(4000),
            null,
            0
        )

        Given("상태 변화") {
            When("Active") {
                defaultTestItemInfo.makeActiveStatus()
                Then("Active 로 바뀌어야한다") {
                    defaultTestItemInfo.itemStatus shouldBe ItemStatus.ACTIVE_AUCTION
                }
            }

            When("Fail") {
                defaultTestItemInfo.makeFailStatus()
                Then("Fail 로 바뀌어야한다") {
                    defaultTestItemInfo.itemStatus shouldBe ItemStatus.FAILED_AUCTION
                }
            }

            When("End") {
                defaultTestItemInfo.makeEndStatus()
                Then("End 로 바뀌어야한다") {
                    defaultTestItemInfo.itemStatus shouldBe ItemStatus.END_AUCTION
                }
            }

            When("입찰시 아이템 상태 변화") {
                val bidCount = defaultTestItemInfo.bidCount
                val testMember = "TestMember"
                val targetAmount = BigDecimal(2000)
                defaultTestItemInfo.changeBidItemInfo(targetAmount, testMember)

                Then("bidCount가 증가해야한다") {
                    defaultTestItemInfo.bidCount shouldBe bidCount + 1
                }
                Then("member 이름이 변해야한다") {
                    defaultTestItemInfo.highestBidMemberName shouldBe testMember
                }
                Then("금액이 변해야한다") {
                    defaultTestItemInfo.realTimePrice shouldBe targetAmount
                }
            }

            When("아이템 수정 상태 변화") {
                val afterModifyItem = Item (
                    itemId = 2,
                    memberId = 1,
                    name = "ModifyItem",
                    itemStatus = ItemStatus.PREPARE_AUCTION,
                    basePrice = BigDecimal(2000),
                    realTimePrice = BigDecimal.ZERO,
                    desiredPrice = BigDecimal(7000),
                    minimumPrice = BigDecimal(6000),
                    null,
                    0
                )
                defaultTestItemInfo.modifyItem(
                    afterModifyItem.name,
                    afterModifyItem.basePrice,
                    afterModifyItem.desiredPrice,
                    afterModifyItem.minimumPrice,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

                Then("수정한 정보로 바뀌어야 한다") {
                    defaultTestItemInfo.name shouldBe afterModifyItem.name
                    defaultTestItemInfo.basePrice shouldBe afterModifyItem.basePrice
                    defaultTestItemInfo.desiredPrice shouldBe afterModifyItem.desiredPrice
                    defaultTestItemInfo.minimumPrice shouldBe afterModifyItem.minimumPrice
                }

            }
        }

        Given("valid 체크") {
            When("경매 시도시 입찰 가격 valid check") {
                defaultTestItemInfo.realTimePrice = BigDecimal(2000)
                Then("현재 가격보다 입찰 시도 가격이 높을 경우") {
                    defaultTestItemInfo.checkValidPrice(BigDecimal(2500)) shouldBe true
                }
                Then("현재 가격보다 입찰 시도 가격이 낮을 경우") {
                    defaultTestItemInfo.checkValidPrice(BigDecimal(1500)) shouldBe false
                }
                Then("현재 가격보다 입찰 시도 가격이 같을 경우") {
                    defaultTestItemInfo.checkValidPrice(BigDecimal(2000)) shouldBe false
                }
            }

            When("경매 시도시 입찰 멤버 valid check") {
                Then("자기 자신의 아이템 입찰을 시도할 경우") {
                    defaultTestItemInfo.checkValidMember(1) shouldBe false
                }
                Then ("타인의 아이템을 입찰 시도할 경우") {
                    defaultTestItemInfo.checkValidMember(2) shouldBe true
                }
            }
        }

        Given("객체 생성 테스트") {
            val makeItem = Item.makeItem (
                memberId = 1,
                itemName = "makingItem",
                basePrice = BigDecimal(1000),
                desiredPrice = BigDecimal(6000),
                minimumPrice = BigDecimal(4000),
                LocalDateTime.now(),
                LocalDateTime.now()
            )

            Then("만들어진 아이템 값 검증") {
                makeItem.memberId shouldBe 1
                makeItem.name shouldBe "makingItem"
                makeItem.basePrice shouldBe BigDecimal(1000)
                makeItem.desiredPrice shouldBe BigDecimal(6000)
                makeItem.minimumPrice shouldBe BigDecimal(4000)
            }
        }
    }
}