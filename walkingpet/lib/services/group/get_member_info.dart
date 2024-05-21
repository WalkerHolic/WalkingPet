import 'dart:convert';
import '../Interceptor.dart';

Future<List<dynamic>> getGroupMember(groupId) async {
  final client = AuthInterceptor();
  final response = await client
      .get(Uri.parse('https://walkingpet.co.kr/team/members/$groupId'));
  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    return jsonData['data'];
  } else {
    throw Error();
  }
}
