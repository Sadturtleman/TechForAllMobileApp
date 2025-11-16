package com.example.myapplication.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.factory.PurchaseTaxiFactory
import com.example.myapplication.data.model.PurchaseTaxi

@Composable
fun PurchaseCard(
    purchase: PurchaseTaxi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(width = 2.dp, color = Color(0xFF1B2A41), shape = RoundedCornerShape(24.dp))
            .padding(20.dp) // 카드 안쪽 여백
    ) {
        Column {
            // 날짜 (예: "10월 11일 탑승")
            Text(
                text = "${purchase.arriveDate.monthNumber}월 ${purchase.arriveDate.dayOfMonth}일 탑승",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFF1B2A41)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "도착장소 : ${purchase.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1B2A41)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "결제 금액 : %,d원".format(purchase.money),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1B2A41)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "자세히 보기",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFC933)
            )
        }
    }
}


@Preview
@Composable
private fun PurchaseCardPreview(){
    val factory = PurchaseTaxiFactory()
    val purchaseList = factory.getPurchaseTaxiList()
    PurchaseCard(purchaseList[0])
}