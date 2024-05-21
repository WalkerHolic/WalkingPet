import 'dart:convert';
import '../Interceptor.dart';

const String baseUrl = "https://walkingpet.co.kr";

// 랭킹 Top10 조회
Future<Map<String, dynamic>> getTop10({String timeframe = 'realtime'}) async {
  final client = AuthInterceptor();
  final url = Uri.parse('$baseUrl/ranking/person/top10?value=$timeframe');
  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    return jsonData;
  } else {
    throw Error();
  }
}

// 랭킹 본인 순위 조회
Future<Map<String, dynamic>> getMyRank({String timeframe = 'realtime'}) async {
  final client = AuthInterceptor();
  final url = Uri.parse('$baseUrl/ranking/person/myrank?value=$timeframe');
  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    return jsonData;
  } else {
    throw Error();
  }
}

// 랭킹 1~3위 조회
Future<Map<String, dynamic>> getTop3({String timeframe = 'realtime'}) async {
  final client = AuthInterceptor();
  final url = Uri.parse('$baseUrl/ranking/person/top3?value=$timeframe');
  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    return jsonData;
  } else {
    throw Error();
  }
}
