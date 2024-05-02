import 'package:http/http.dart' as http;
import 'dart:convert';

const String baseUrl = "https://walkingpet.co.kr";
const String yesterdayTop10 = "ranking/person/top10?value=yesterday";
const String yesterdayMyRank = "ranking/person/myrank?value=yesterday&userId=1";
const String yesterdayTop3 = "ranking/person/top3?value=yesterday";

// 랭킹 Top10 조회
Future<Map<String, dynamic>> getTop10() async {
  final url = Uri.parse('$baseUrl/$yesterdayTop10');
  final response = await http.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    // print(jsonData);
    return jsonData;
  } else {
    print("개인랭킹 어제자 Top10 오류: ${response.statusCode}");
    throw Error();
  }
}

// 랭킹 본인 순위 조회
Future<Map<String, dynamic>> getMyRank() async {
  final url = Uri.parse('$baseUrl/$yesterdayMyRank');
  final response = await http.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    // print(jsonData);
    return jsonData;
  } else {
    print("개인랭킹 어제자 유저순위(my rank) 오류: ${response.statusCode}");
    throw Error();
  }
}

// 랭킹 1~3위 조회
Future<Map<String, dynamic>> getTop3() async {
  final url = Uri.parse('$baseUrl/$yesterdayTop3');
  final response = await http.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    print(jsonData);
    return jsonData;
  } else {
    print("개인랭킹 어제자 Top3 오류: ${response.statusCode}");
    throw Error();
  }
}
