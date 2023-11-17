package ru.smak.lazyview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.smak.lazyview.ui.theme.LazyViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lstForRow = SnapshotStateList<String>()
            val lstForCol = SnapshotStateList<String>()
            LazyViewTheme {
                MainUI(
                    Modifier.fillMaxSize(),
                    onAddItemToRow = {
                        lstForRow.add("Строка ${lstForRow.size+1}")
                    },
                    onAddItemToCol = {
                        lstForCol.add("Строка ${lstForCol.size+1}")
                    }
                ){
                    LazyR(
                        lstForRow.reversed(),
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    )
                    LazyC(
                        lstForCol.reversed(),
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    modifier: Modifier = Modifier,
    onAddItemToRow: ()->Unit = {},
    onAddItemToCol: ()->Unit = {},
    content: @Composable ColumnScope.()->Unit,
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_title))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_splitscreen_24),
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    FilledIconButton(onClick = onAddItemToRow) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_playlist_add_24),
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItemToCol) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_playlist_add_24),
                    contentDescription = null,
                )
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            content()
        }
    }
}

@Composable
fun LazyC(
    lst: List<String>,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        item{
            Text(
                text = "Ленивый столбец:",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        items(lst){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 8.dp, vertical = 32.dp),
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Preview
@Composable
fun LazyCPreview(){
    LazyC(listOf("1", "2", "3", "4", "5").reversed(), Modifier.fillMaxSize())
}

@Composable
fun LazyR(
    lst: List<String>,
    modifier: Modifier = Modifier,
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.lazy_row),
            modifier = Modifier
                .padding(8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(lst) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 8.dp, vertical = 32.dp),
                        fontSize = 14.sp
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun LazyRPreview(){
    LazyR(
        listOf("1", "2", "3"),
        Modifier.fillMaxWidth()
    )
}
