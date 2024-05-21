import 'dart:convert';
import '../Interceptor.dart';

Future<List<Map<String, dynamic>>> getSearchResult(groupName) async {
  final client = AuthInterceptor();
  final response = await client.get(
      Uri.parse('https://walkingpet.co.kr/team/search?content=$groupName'));
  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    // jsonData['data']를 List<Map<String, dynamic>>으로 변환
    List<Map<String, dynamic>> result = List<Map<String, dynamic>>.from(
        jsonData['data'].map((item) => Map<String, dynamic>.from(item)));
    return result;
  } else {
    throw Error();
  }
}
